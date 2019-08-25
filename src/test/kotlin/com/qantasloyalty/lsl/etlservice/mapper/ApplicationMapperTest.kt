package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.dynamodbv2.model.ScanResult
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

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
            println(applicationData.toBelow90AppCsvString())
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