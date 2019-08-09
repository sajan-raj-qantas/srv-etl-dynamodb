package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Extractor @Autowired constructor(private val dynamoDB: AmazonDynamoDB) {

    suspend fun CoroutineScope.extractData(): ReceiveChannel<ScanResult> =produce {

        // Initialize the rate limiter to allow 25 read capacity units / sec
        val rateLimiter = RateLimiter.create(5.0)

        // Track how much throughput we consume on each page
        var permitsToConsume = 1

        // Initialize the pagination token
        var exclusiveStartKey: Map<String, AttributeValue>? = null

        do {
            // Let the rate limiter wait until our desired throughput "recharges"
            rateLimiter.acquire(permitsToConsume)

            // Do the scan
            val scan = ScanRequest()
                    .withTableName("avro-dev-integration-motorapplication-application")
                    //.withFilterExpression()
                    .withLimit(100)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                    .withExclusiveStartKey(exclusiveStartKey)
            val result = dynamoDB!!.scan(scan)
            exclusiveStartKey = result.lastEvaluatedKey

            // Account for the rest of the throughput we consumed,
            // now that we know how much that scan request cost
            val consumedCapacity = result.consumedCapacity.capacityUnits!!
            permitsToConsume = (consumedCapacity - 1.0).toInt()
            if (permitsToConsume <= 0) {
                permitsToConsume = 1
            }
            println("Read result -$result")
            send(result)
            //Stream.generate { result }


        } while (exclusiveStartKey != null)

    }

}