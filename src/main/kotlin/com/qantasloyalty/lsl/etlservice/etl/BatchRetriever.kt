package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataKey
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataNew
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

@Component
class BatchRetriever(@Autowired val awsdynamoDB: AmazonDynamoDB,
                     @Autowired val applicationMapper: ApplicationMapper) {

    fun batchGetItems(applicationKeys: List<ApplicationDataKey>): List<ApplicationDataNew> {
        var applicationDataList = ArrayList<ApplicationDataNew>()
        val startTime = System.currentTimeMillis()
        val request = BatchGetItemRequest().addRequestItemsEntry(APPLICATION_DATA_TABLE_NAME, KeysAndAttributes())
        applicationKeys.forEach { (applicationId, lastDataCreatedTimestamp) ->
            val keysAndAttributes = request.requestItems[APPLICATION_DATA_TABLE_NAME]
            val keyMap = HashMap<String, AttributeValue>()
            keyMap["applicationId"] = AttributeValue(applicationId)
            keyMap["createdTimestamp"] = AttributeValue(lastDataCreatedTimestamp)
            keysAndAttributes?.withKeys(keyMap)
        }
        val batchGetItemResult = awsdynamoDB!!.batchGetItem(request)
        val dataMapList = batchGetItemResult.responses[APPLICATION_DATA_TABLE_NAME]
        val elapsedTime = System.currentTimeMillis() - startTime
        println("BatchGet finished in " + elapsedTime + "ms")
        dataMapList?.forEach {
            applicationDataList.add(applicationMapper.fromAttributeValuesToApplicationData(it))
        }
        println("Mapped " + applicationDataList.size + "items to ApplicationData")
        return applicationDataList
    }

    companion object {
        private val APPLICATION_DATA_TABLE_NAME = "avro-dev-integration-motorapplication-application-data"
    }
}

