package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionContext
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.EncryptionFlags
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.DirectKmsMaterialProvider
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.kms.AWSKMSClientBuilder
import org.springframework.stereotype.Component

@Component
class ApplicationDataDecryptor {
        val credentialsProvider = DefaultAWSCredentialsProviderChain()
        val kms = AWSKMSClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .build()

        val materialsProvider = DirectKmsMaterialProvider(kms, null)
        val encryptor = DynamoDBEncryptor.getInstance(materialsProvider)

        val encryptionContext = EncryptionContext.Builder()
                .withTableName("avro-dev-integration-motorapplication-application-data")
                .withHashKeyName("applicationId")
                .withRangeKeyName("createdTimestamp")
                .build()

        val attributeFlags: Map<String, Set<EncryptionFlags>> = mapOf(
                "attributes" to setOf(EncryptionFlags.ENCRYPT, EncryptionFlags.SIGN)
        )

    fun decrypt(item: Map<String, AttributeValue>): Map<String, AttributeValue> {
        return encryptor.decryptRecord(item, attributeFlags, encryptionContext)
    }
}
