package com.qantasloyalty.lsl.etlservice.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class ApplicationDataTest {


    @Test
    fun testDelegate() {
        val attributes = mapOf(
                "applicationId" to AttributeValue().withS("test")
        )

        val applicationData = ApplicationData(attributes)
        println(applicationData)
        println(applicationData.applicationId)
    }
}