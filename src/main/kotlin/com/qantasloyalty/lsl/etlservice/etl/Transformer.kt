package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationDataDecryptor
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataKey
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
import java.util.HashMap
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
import com.amazonaws.services.dynamodbv2.model.AttributeValue


@Component
class Transformer(@Autowired var applicationMapper: ApplicationMapper,
                  @Autowired var dynamoDB: DynamoDB,
                  @Autowired var awsdynamoDB: AmazonDynamoDB,
                  @Autowired var decryptor: ApplicationDataDecryptor) {

    private val APPLICATION_DATA_TABLE_NAME = "avro-dev-integration-motorapplication-application-data"

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)
    private val above90AppBuffer = ScanResultBuffer("above90AppBuffer", 2)
    private val above90PartyBuffer = ScanResultBuffer("above90PartyBuffer", 2)
    private val below90AppBuffer = ScanResultBuffer("below90AppBuffer", 2)
    private val below90PartyBuffer = ScanResultBuffer("below90PartyBuffer", 2)

    fun transformScan(scanResult: ScanResult, processorType: TransformType) {
        when (processorType) {
            TransformType.BELOW_90 -> {
                transformForAppBelow90(scanResult)
                transformForPartyBelow90(scanResult)
            }

            TransformType.ABOVE_90 -> {
                transformForAppAbove90(scanResult)
                transformForPartyAbove90(scanResult)
            }
        }
    }

    private fun transformForAppAbove90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppAbove90 using mapper: $applicationId")

        //Collect all Application table data for items in ScanResult
        val applicationList = scanResult.items.stream()
                .map(applicationMapper::fromAttributeValuesToApplication)
                //.map ()
                .collect(Collectors.toList())
        println("Application List :: $applicationList")
        //Collect all ids
        val applicationDataKeys = applicationList.stream()
                .map { ApplicationDataKey(it.applicationId, it.lastDataCreatedTimestamp) }
                .collect(Collectors.toList())
        val batchGetItems: List<ApplicationData>? = batchGetItems(applicationDataKeys)
        println("BatchGetItems: $batchGetItems")
       // pushToBuffer(scanResult, above90AppBuffer)
    }

    private fun batchGetItems(applicationKeys: MutableList<ApplicationDataKey>): List<ApplicationData>? {
        val request = BatchGetItemRequest().addRequestItemsEntry(APPLICATION_DATA_TABLE_NAME, KeysAndAttributes())
        applicationKeys.forEach {
            val keysAndAttributes: KeysAndAttributes? = request.requestItems.get(APPLICATION_DATA_TABLE_NAME)
            //keysAndAttributes?.withKeys("applicationId:${it.applicationId}")
            keysAndAttributes?.withKeys(
                    mapOf(
                            "applicationId" to AttributeValue(it.applicationId),
                            "createdTimestamp" to AttributeValue(it.lastDataCreatedTimestamp)
                    )
            )
        }
        val batchGetItemResult: BatchGetItemResult = awsdynamoDB.batchGetItem(request)
        println(batchGetItemResult)
        val items: List<MutableMap<String, AttributeValue>>? = batchGetItemResult.responses.get(APPLICATION_DATA_TABLE_NAME)
        println("BatchGetResponse: $items")
        val collect: List<ApplicationData>? = items?.stream()?.map(applicationMapper::fromAttributeValues)?.collect(Collectors.toList())
        return collect
    }

    private fun pushToBuffer(scanResult: ScanResult, scanResultBuffer: ScanResultBuffer) {
        scanResultBuffer.pushToBuffer(scanResult)
    }

    private fun transformForAppBelow90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppBelow90: $applicationId")
        pushToBuffer(scanResult, below90AppBuffer)
    }

    private fun transformForPartyAbove90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForPartyBelow90: $applicationId")
        pushToBuffer(scanResult, above90PartyBuffer)
    }

    private fun transformForPartyBelow90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForPartyBelow90: $applicationId")
        pushToBuffer(scanResult, below90PartyBuffer)
    }

    data class ScanResultBuffer(val name: String, val capacity: Int) {
        //Pass s3 details here?
        val buffer = HashSet<ScanResult>(capacity)

        @Synchronized
        fun pushToBuffer(scanResult: ScanResult) {
            if (buffer.size > capacity) {
                upload()
                emptyBuffer()
            }
            push(scanResult)
        }

        @Synchronized
        private fun push(scanResult: ScanResult) {
            buffer.add(scanResult)
        }

        @Synchronized
        fun emptyBuffer() {
            buffer.clear()
            println("Cleared contents of Buffer $name..")
        }

        @Synchronized
        fun upload() {
            println("Uploading contents of Buffer $name..")
            for (scanResult in buffer) {
                //TODO upload to S3
                val tm = TransferManagerBuilder.standard()
                        .withS3Client(null)
                        .withMultipartUploadThreshold((5 * 1024 * 1025).toLong())
                        .build()
                //Upload using multipart
                //tm.upload()
            }

        }
    }

}