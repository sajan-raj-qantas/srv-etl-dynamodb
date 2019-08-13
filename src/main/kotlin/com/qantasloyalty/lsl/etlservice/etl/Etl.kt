package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class Etl @Autowired constructor(
        private val dynamoDB: AmazonDynamoDB,
        private val extractor: Extractor,
        private val transformer: Transformer) {

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)

    suspend fun CoroutineScope.export(threads: Int, id: Int): ReceiveChannel<ScanResultWrapper> = produce {
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
//            println("Read result -$result")
            channel.send(ScanResultWrapper(id, iterations, result))
            //Stream.generate { result }

            println("id $id on iteration $iterations")
            iterations = iterations.inc()
        } while (exclusiveStartKey != null && iterations < 5)

    }

    private fun CoroutineScope.createConsumers(): SendChannel<ScanResultWrapper> {
        val broadcastChannel = BroadcastChannel<ScanResultWrapper>(BUFFERED)
        launch {
            broadcastChannel.consumeEach {
                println("Channel 1 - ${it.segment}:: ${it.iteration} - Got ${it.scanResult.count} items:: ${it.scanResult.lastEvaluatedKey}")
            }
        }

        launch {
            broadcastChannel.consumeEach {
                println("Channel 2 - ${it.segment}:: ${it.iteration} - Got ${it.scanResult.count} items:: ${it.scanResult.lastEvaluatedKey}")
            }
        }
        return broadcastChannel
    }

    fun doEtl(threads: Int) = runBlocking {
        val producers = mergeChannels((0..threads).map { threadId ->
            export(threads, threadId)
        })

        /** How can this be refactored?? **/
        val consumerChannel = createConsumers()
        /** End of what needs to be refactored **/

        producers.consumeEach {
            consumerChannel.send(it)
        }

        println("done - closing broadcast channel")
        consumerChannel.close()
    }

    private fun <T> CoroutineScope.mergeChannels(channels: Collection<ReceiveChannel<T>>) : ReceiveChannel<T> {
        return produce {
            channels.forEach {
                launch { it.consumeEach { send(it) } }
            }
        }
    }

}