package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.*
import com.google.common.util.concurrent.RateLimiter
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationDataDecryptor
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class ApplicationMapperTest {

    @Autowired
    lateinit var decryptor: ApplicationDataDecryptor

    @Autowired
    lateinit var mapper: ApplicationMapper

    @Autowired
    lateinit var dynamoDB: AmazonDynamoDB

    val client = AmazonDynamoDBClientBuilder.defaultClient()

    @Test
    fun testApplicationMapper() {
        val item: Map<String, AttributeValue> = GetItemRequest("avro-dev-integration-motorapplication-application-data",
                mapOf(
                        "applicationId" to AttributeValue("QM10TY43KB"),
                        "createdTimestamp" to AttributeValue("2019-08-12T16:45:29.999")
                ))
                .let { client.getItem(it) }
                .let { it.item }
        println("")
        println("Before Decrypt- $item")
        val applicationData = mapper.fromAttributeValues(item)
        println("ApplicationData: $applicationData")
    }

    private fun getScanResult(): ScanResult? {
        // Initialize the pagination token
        var exclusiveStartKey: Map<String, AttributeValue>? = null

            // Do the scan
            val scan = ScanRequest()
                    .withTableName("avro-dev-integration-motorapplication-application-data")
                    //.withFilterExpression()
                    .withLimit(1)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                    .withExclusiveStartKey(exclusiveStartKey)

            val result = dynamoDB!!.scan(scan)
            exclusiveStartKey = result.lastEvaluatedKey

            println("Read result -$result")
            return result
    }
}