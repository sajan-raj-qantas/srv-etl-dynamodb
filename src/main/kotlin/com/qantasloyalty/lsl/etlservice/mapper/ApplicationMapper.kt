package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.springframework.stereotype.Component
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import org.springframework.beans.factory.annotation.Autowired

@Component
class ApplicationMapper(@Autowired val decryptor: ApplicationDataDecryptor) {

    fun fromAttributeValues(attributesMap: Map<String, AttributeValue>): ApplicationData {

        val decrypt = decryptor.decrypt(attributesMap)
//        return ApplicationData(transform(decrypt))
        return ApplicationData(decrypt)
    }

    private fun transform(attributesMap: Map<String, AttributeValue>): MutableMap<String, Any?> {
        val transformedMap = mutableMapOf<String, Any?>()
        attributesMap.entries.forEach {
            transformedMap.put(it.key, it.value?.s ?: (it.value?.n ?: it.value.b))
        }
        return transformedMap
    }

}

