package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EtlService @Autowired constructor(
        private val dynamoDB: AmazonDynamoDB,
        private val extractor: Extractor,
        private val transformer: Transformer){

    private val LOG = LoggerFactory.getLogger(object{}::class.java.`package`.name)

    suspend fun export(threads: Int, id: Int, channel: SendChannel<ScanResult>) {
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
                    .withTotalSegments(threads + 1)
                    .withSegment(id)
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
            channel.send(result)
            //Stream.generate { result }


        } while (exclusiveStartKey != null)
    }

    fun doEtl(threads: Int) = runBlocking {
        val receiveChannel = Channel<ScanResult>()

        for (i in 0..threads) {
            launch { export(threads, i, receiveChannel)}
        }

        val below90 = Channel<ScanResult>()
        val above90 = Channel<ScanResult>()

        receiveChannel.consumeEach {
            below90.send(it)
            above90.send(it)
        }

        kotlinx.coroutines.delay(10000)
        coroutineContext.cancelChildren()
    }

    fun doEtl() = GlobalScope.launch{
        val channel: ReceiveChannel<ScanResult> = extract()
        launchProcessor(TransformType.PARTY_ABOVE_90, channel)
        launchProcessor(TransformType.APP_ABOVE_90, channel)
        launchProcessor(TransformType.PARTY_BELOW_90, channel)
        launchProcessor(TransformType.APP_BELOW_90, channel)
        println("Launched 4 processors...")
    }

    //TODO FIX : Having trouble calling this function from extractor.
    fun CoroutineScope.extract(): ReceiveChannel<ScanResult> =produce {

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

    fun CoroutineScope.launchProcessor(processorType: TransformType, channel: ReceiveChannel<ScanResult>) = launch {
        transformer.transformScan(channel, processorType)
    }

}

