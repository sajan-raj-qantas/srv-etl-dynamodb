package com.qantasloyalty.lsl.etlservice.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestConfig {

    @Bean
    fun objectMapper():ObjectMapper{
        /*return ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                //.registerModule(KotlinModule())*/
        return jacksonObjectMapper()
                .apply {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    setSerializationInclusion(JsonInclude.Include.NON_NULL)
                   // registerModules(JavaTimeModule(), ParameterNamesModule())
                }
    }
}