package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EtlService @Autowired constructor(
        private val dynamoDB: AmazonDynamoDB,
        private val extractor: Extractor,
        private val transformer: Transformer) {

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)

    suspend fun export(threads: Int, id: Int, channel: SendChannel<ScanResultWrapper>) {
        val rateLimiter = RateLimiter.create(5.0)

        // Track how much throughput we consume on each page
        var permitsToConsume = 1

        // Initialize the pagination token
        var exclusiveStartKey: Map<String, AttributeValue>? = null

        var iterations = 1
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
           // println("Read result -$result")
            channel.send(ScanResultWrapper(id, iterations, result))
            //Stream.generate { result }

            iterations = iterations.inc()
            println("id $id on iteration $iterations")
        } while (exclusiveStartKey != null && iterations < 25 && !channel.isClosedForSend)

        channel.close()
        transformer.closeDestinationStreams()
        println("###Closed Send channel $id###")
    }

    fun doEtl(threads: Int) = runBlocking {
        val receiveChannel = Channel<ScanResultWrapper>()
        val jobs = mutableListOf<Job>()

        jobs += launch {
            receiveChannel.consumeEach {
                println("${it.segment}:: ${it.iteration} - Got ${it.scanResult.count} items:: ${it.scanResult.lastEvaluatedKey}")
                processScanResult(it)
            }
        }

        for(i in 0..threads){
            jobs += launch { export(threads,i,receiveChannel) }
        }
        jobs.joinAll() //is this right?
    }

    private fun processScanResult(scanResultWrapper: ScanResultWrapper) {
        println("Below90 - ${scanResultWrapper.segment}:: ${scanResultWrapper.iteration}")
        transformer.transformScan(scanResultWrapper.scanResult)
    }
}

data class ScanResultWrapper(val segment: Int, val iteration: Int, val scanResult: ScanResult)
/* val countdownLatch = AtomicInteger(threads + 1)
 for (i in 0..threads) {
     jobs += async { export(threads, i, receiveChannel) }.also {
         it.invokeOnCompletion {
             if (countdownLatch.decrementAndGet() == 0) {
                 receiveChannel.close()
                 println("Closed channel")
             }
             println("Completed $i")
         }
     }
}*/