package com.qantasloyalty.lsl.etlservice.mapper

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.qantasloyalty.lsl.etlservice.model.Application
import org.springframework.stereotype.Component
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import org.springframework.beans.factory.annotation.Autowired

@Component
class ApplicationMapper(@Autowired val decryptor: ApplicationDataDecryptor,
                        @Autowired val objectMapper: ObjectMapper) {

    fun fromAttributeValues(attributesMap: Map<String, AttributeValue>): ApplicationData {

       //val decrypt = decryptor.decrypt(attributesMap)
       //println("After Decrypt: $decrypt")
        val transformedMap = transform(attributesMap)
        println("Transformed Application Data Map: $transformedMap")
        return ApplicationData().fromApplicationMap(transformedMap)
      //  return ApplicationData(decrypt)
    }

    fun fromAttributeValuesToApplication(attributesMap: Map<String, AttributeValue>): Application {
        val transformedMap = transform(attributesMap)
        //println("Transformed Application Map: $transformedMap")
        return objectMapper.convertValue<Application>(transformedMap,Application::class.java)
    }

    private fun transform(attributesMap: Map<String, AttributeValue>): MutableMap<String, Any?> {
        val transformedMap = mutableMapOf<String, Any?>()
        attributesMap.entries.forEach {
            transformedMap.put(it.key, it.value?.s ?: (it.value?.n ?: it.value.b))
        }
        return transformedMap
    }

}

