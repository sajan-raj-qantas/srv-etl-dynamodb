package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationDataDecryptor
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataKey
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
import com.amazonaws.services.dynamodbv2.model.AttributeValue


@Component
class Transformer(@Autowired var applicationMapper: ApplicationMapper,
                  @Autowired var dynamoDB: DynamoDB,
                  @Autowired var awsdynamoDB: AmazonDynamoDB,
                  @Autowired var decryptor: ApplicationDataDecryptor) {

    private val APPLICATION_DATA_TABLE_NAME = "avro-dev-integration-motorapplication-application-data"

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)
    private val above90AppBuffer = ApplicationDataUploadOutputStream("above90AppBuffer", 2)
    private val above90PartyBuffer = ApplicationDataUploadOutputStream("above90PartyBuffer", 2)
    private val below90AppBuffer = ApplicationDataUploadOutputStream("below90AppBuffer", 2)
    private val below90PartyBuffer = ApplicationDataUploadOutputStream("below90PartyBuffer", 2)

    //private val below90AppBuffer = S3MultipartUploadOutputStream("below90AppBuffer","avro-file-transfer","etl/motor-outgoing-c2-below90.csv")

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
        pushListToBuffer(batchGetItems, above90AppBuffer)
    }

    private fun pushListToBuffer(batchGetItems: List<ApplicationData>?, above90AppUploadOutputStream: ApplicationDataUploadOutputStream) {
        batchGetItems?.forEach { above90AppUploadOutputStream.pushToBuffer(it) }
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

  /*  private fun pushListToBuffer(applicationData: ApplicationData, uploadOutputStream: ApplicationDataUploadOutputStream) {
        uploadOutputStream.pushToBuffer(uploadOutputStream)
    }*/
    private fun pushListToBuffer(scanResult: ScanResult, applicationDataUploadOutputStream: ApplicationDataUploadOutputStream) {
       // applicationDataUploadOutputStream.pushToBuffer(scanResult)
    }

    private fun transformForAppBelow90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppBelow90: $applicationId")
        pushListToBuffer(scanResult, below90AppBuffer)
        //below90AppBuffer.write()
    }

    private fun transformForPartyAbove90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForPartyBelow90: $applicationId")
        pushListToBuffer(scanResult, above90PartyBuffer)
    }

    private fun transformForPartyBelow90(scanResult: ScanResult) {
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForPartyBelow90: $applicationId")
        pushListToBuffer(scanResult, below90PartyBuffer)
    }



}