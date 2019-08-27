package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.qantasloyalty.lsl.etlservice.model.*
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

@Component
class ApplicationMapper(@Autowired val decryptor: ApplicationDataDecryptor,
                        @Autowired val objectMapper: ObjectMapper) {

    fun fromAttributeValuesToApplication(attributesMap: Map<String, AttributeValue>): Application {
        val transformedMap = transform(attributesMap)
        return objectMapper.convertValue(transformedMap, Application::class.java)
    }

    fun fromAttributeValuesToApplicationData(attributesMap: Map<String, AttributeValue>): ApplicationData {
        val decrypt = decryptor.decrypt(attributesMap)
        val transformedMap = transform(decrypt)
        var applicationDataNew = ApplicationData()
        //println(transformedMap)
        try {
            applicationDataNew = objectMapper.convertValue(transformedMap, ApplicationData::class.java)
            if(transformedMap.containsKey("attributes")) {
                var attributesString: String = transformedMap.get("attributes") as String
                val applicationAttributes = objectMapper.readValue(attributesString, ApplicationAttributes::class.java)
                applicationDataNew.attributes = applicationAttributes
            }
            if(transformedMap.containsKey("pricing")) {
                var pricingString: String = transformedMap.get("pricing") as String
                val pricing = objectMapper.readValue(pricingString, Pricing::class.java)
                applicationDataNew.pricing = pricing
            }
        } catch (e: Exception) {
            println("Json parse error: ${e.message}")
            e.printStackTrace()
        }
        return applicationDataNew
    }

    private fun transform(attributesMap: Map<String, AttributeValue>): MutableMap<String, Any?> {
        val transformedMap = mutableMapOf<String, Any?>()
        attributesMap.entries.forEach {
            transformedMap.put(it.key, it.value?.s ?: (it.value?.n ?: it.value.b))
        }
        return transformedMap
    }

}

