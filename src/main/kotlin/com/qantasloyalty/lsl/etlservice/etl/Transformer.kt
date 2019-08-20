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
import com.google.common.collect.Lists


@Component
class Transformer(@Autowired var applicationMapper: ApplicationMapper,
                  @Autowired var dynamoDB: DynamoDB,
                  @Autowired var awsdynamoDB: AmazonDynamoDB,
                  @Autowired var decryptor: ApplicationDataDecryptor) {

    private val APPLICATION_DATA_TABLE_NAME = "avro-dev-integration-motorapplication-application-data"

    val bucketName = "avro-file-transfer"
    val above90AppKeyName = "etl/Car90DayFile-Application.csv"
    val above90PartyKeyName = "etl/Car90DayFile-Party.csv"
    val below90AppKeyName = "etl/Car-Application.csv"
    val below90PartykeyName = "etl/Car-Party.csv"

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)
    private val above90AppStream = S3MultipartUploadBufferedOutputStream(bucketName, above90AppKeyName)
    private val above90PartyStream = S3MultipartUploadBufferedOutputStream(bucketName, above90PartyKeyName)
    private val below90AppStream = S3MultipartUploadBufferedOutputStream(bucketName, below90AppKeyName)
    private val below90PartyStream = S3MultipartUploadBufferedOutputStream(bucketName, below90PartykeyName)

    //private val below90AppStream = S3MultipartUploadBufferedOutputStream("below90AppStream","avro-file-transfer","etl/motor-outgoing-c2-below90.csv")

    fun transformScan(scanResult: ScanResult, processorType: TransformType) {
        //Collect all Application table data for items in ScanResult
        val applicationList = scanResult.items.stream()
                .map(applicationMapper::fromAttributeValuesToApplication)
                .collect(Collectors.toList())
        //println("Application List :: $applicationList")
        //Collect all ids
        val applicationDataKeys = applicationList.stream()
                .map { ApplicationDataKey(it.applicationId, it.lastDataCreatedTimestamp) }
                .collect(Collectors.toList())
        val applicationDataList: List<ApplicationData>? = batchGetItems(applicationDataKeys)
        //println("ApplicationDataList: $applicationDataList")

        when (processorType) {
            TransformType.BELOW_90 -> {
                // transformForAppBelow90(applicationDataList)
                // transformForPartyBelow90(applicationDataList)
            }

            TransformType.ABOVE_90 -> {
                //transformForAppAbove90(applicationDataList)
                transformForPartyAbove90(applicationDataList)
            }
        }
    }

    private fun transformForAppAbove90(applicationDataList: List<ApplicationData>?) {
        pushListToBuffer(applicationDataList, above90AppStream)
    }

    private fun transformForAppBelow90(applicationDataList: List<ApplicationData>?) {
        pushListToBuffer(applicationDataList, below90AppStream)
    }

    private fun transformForPartyAbove90(applicationDataList: List<ApplicationData>?) {
        pushListToBuffer(applicationDataList, above90PartyStream)
    }

    private fun transformForPartyBelow90(applicationDataList: List<ApplicationData>?) {
        pushListToBuffer(applicationDataList, below90PartyStream)
    }

    private fun pushListToBuffer(batchGetItems: List<ApplicationData>?, uploadOutputStream: S3MultipartUploadBufferedOutputStream) {
        println("Pushing ${batchGetItems?.size} items to buffer")
        batchGetItems?.forEach {
            val bytes = it.toCsvString().toByteArray()
            //println(bytes)
            uploadOutputStream.write(bytes)
        }
    }

    private fun batchGetItems(applicationKeys: MutableList<ApplicationDataKey>): List<ApplicationData>? {
        var items = ArrayList<MutableMap<String, AttributeValue>>()
        val choppedLists = Lists.partition(applicationKeys, 100)
        println("Preparing ${choppedLists.size} BatchGETRequest(s) for ${applicationKeys.size} applications")
        choppedLists.forEach {
            val request = BatchGetItemRequest().addRequestItemsEntry(APPLICATION_DATA_TABLE_NAME, KeysAndAttributes())
            it.forEach {
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
            val mutableList = batchGetItemResult.responses.get(APPLICATION_DATA_TABLE_NAME)
            if (mutableList != null) {
                items.addAll(mutableList)
                //println("Got Batch Get Response for ${it.size} applications")
            }
        }

        println("Got All Batch Get Responses for ${items.size} applications")
        val collect: List<ApplicationData>? = items?.stream()?.map(applicationMapper::fromAttributeValues)?.collect(Collectors.toList())
        println("Mapped ${items.size} items")
        return collect
    }

}