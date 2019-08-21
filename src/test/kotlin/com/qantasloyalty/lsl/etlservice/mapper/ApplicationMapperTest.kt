package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.*
import com.google.common.util.concurrent.RateLimiter
import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsv
import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationDataDecryptor
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataNew
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.StringWriter

@SpringBootTest
@RunWith(SpringRunner::class)
class ApplicationMapperTest {

    @Autowired
    lateinit var mapper: ApplicationMapper

    @Autowired
    lateinit var dynamoDB: AmazonDynamoDB

    @Test
    fun testApplicationMapperWithObjectMapper() {
        getScanResult()?.items?.forEach {
            val applicationData = mapper.fromAttributeValuesToApplicationData(it)
            println(applicationData.toCsvString())
        }
    }

    private fun getScanResult(): ScanResult? {
        var exclusiveStartKey: Map<String, AttributeValue>? = null
            val scan = ScanRequest()
                    .withTableName("avro-dev-integration-motorapplication-application-data")
                    //.withFilterExpression()
                    .withLimit(100)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                    .withExclusiveStartKey(exclusiveStartKey)
            val result = dynamoDB!!.scan(scan)
            println("Read result -$result")
            return result
    }
}