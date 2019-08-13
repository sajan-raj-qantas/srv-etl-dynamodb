package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionContext
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionFlags
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.DirectKmsMaterialProvider
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.kms.AWSKMSClientBuilder
import org.junit.Test

class DecryptionTest {

    @Test
    fun testGetAndDecryptItem() {
        val credentialsProvider = DefaultAWSCredentialsProviderChain()
        val kms = AWSKMSClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .build()

        val materialsProvider = DirectKmsMaterialProvider(kms, null)
        val encryptor = DynamoDBEncryptor.getInstance(materialsProvider)


        val client = AmazonDynamoDBClientBuilder.defaultClient()
        val item: Map<String, AttributeValue> = GetItemRequest("avro-dev-integration-motorapplication-application-data",
                mapOf(
                        Pair("applicationId", AttributeValue("QM10TY43KB")),
                        Pair("createdTimestamp", AttributeValue("2019-08-12T16:45:29.999"))
                ))
                .let { client.getItem(it) }
                .let { it.item }
        val attributeFlags: Map<String, Set<EncryptionFlags>> = mapOf(
                Pair("attributes", setOf(EncryptionFlags.ENCRYPT, EncryptionFlags.SIGN))
        )

        val encryptionContext = EncryptionContext.Builder()
                .withTableName("avro-dev-integration-motorapplication-application-data")
                .withHashKeyName("applicationId")
                .withRangeKeyName("createdTimestamp")
                .build()

        val after = encryptor.decryptRecord(item, attributeFlags, encryptionContext)
        println("")
        println("Before - $item")
        println("After - $after")
    }
}