package com.qantasloyalty.lsl.etlservice.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.junit.Test

internal class ApplicationDataTest {


    @Test
    fun testDelegate() {
        val attributes = mapOf(
                "applicationId" to AttributeValue().withS("test")
        )

        val applicationData = ApplicationDataWithDelegate(attributes)
        println(applicationData)
        println(applicationData.applicationId)
        println(applicationData.riskAddressPostcode)

    }
}