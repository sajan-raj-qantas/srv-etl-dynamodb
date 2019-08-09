package com.qantasloyalty.lsl.etlservice.config

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.core.region.RegionProvider
import org.springframework.cloud.aws.core.region.StaticRegionProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonAccountConfig {

    @Bean
    fun accountCredentialsProvider(): AWSCredentialsProvider {
        return DefaultAWSCredentialsProviderChain()
    }

    @Bean
    fun regionProvider(@Value("\${aws.region}") region: String): RegionProvider {
        return StaticRegionProvider(region)
    }
}
