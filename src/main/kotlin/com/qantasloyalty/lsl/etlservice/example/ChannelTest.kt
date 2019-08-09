package com.qantasloyalty.lsl.etlservice.example

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val channel = Channel<Int>()

    launch {
        for (i in 1..5) {
            channel.send(i)
        }
        //channel.close()
    }

    for (i in channel) {
        println("received: $i")
    }
}