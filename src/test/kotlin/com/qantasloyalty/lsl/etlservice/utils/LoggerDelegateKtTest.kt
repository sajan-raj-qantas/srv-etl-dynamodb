package com.qantasloyalty.lsl.etlservice.utils

import org.junit.Test


internal class LoggerDelegateKtTest {
    private val logger by log

    companion object {
        val LOGGER by log
    }

    @Test
    fun testFieldLogger() {
        logger.info("test")
    }

    @Test
    fun testStaticLogger() {
        LOGGER.info("test")
    }
}