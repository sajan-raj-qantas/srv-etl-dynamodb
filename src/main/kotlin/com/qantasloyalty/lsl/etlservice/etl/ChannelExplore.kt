package com.qantasloyalty.lsl.etlservice.etl

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory

class ChannelExplore {

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)
    var sendChannel = Channel<String>()
    var above90Channel = Channel<String>()
    var below90Channel = Channel<String>()

    fun exploreChannel() = runBlocking {

        launch { generateRecords() }

        launch {
            sendChannel.consumeEach {
                println("Received $it")
                above90Channel.send(it)
                below90Channel.send(it)
            }
        }

        launch {
            above90Channel.consumeEach {
                println("Received in Above 90 $it")
            }
        }

        launch {
            below90Channel.consumeEach {
                println("Received in Below 90 $it")
            }
        }
        joinAll()

    }

    private suspend fun generateRecords() {
        repeat(2) {
            val randomString = RandomStringUtils.randomAlphanumeric(5)
            sendChannel.send(randomString)
            println("Sent $randomString")
            Thread.sleep(1000)
        }
        sendChannel.close()
    }

}


