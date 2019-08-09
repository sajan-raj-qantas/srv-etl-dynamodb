package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.model.ScanResult
import kotlinx.coroutines.channels.ReceiveChannel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Transformer {

    private val LOG = LoggerFactory.getLogger(object{}::class.java.`package`.name)

    suspend fun transformScan(channel: ReceiveChannel<ScanResult>, processorType: TransformType) {
        for(scanResult in channel){
            when(processorType){
                TransformType.APP_BELOW_90 -> transformForAppBelow90(scanResult)
                TransformType.PARTY_BELOW_90 -> transformForPartyBelow90(scanResult)
                TransformType.APP_ABOVE_90 -> transformForAppAbove90(scanResult)
                TransformType.PARTY_ABOVE_90 -> transformForPartyAbove90(scanResult)
            }
        }
    }

    fun transformForAppAbove90(scanResult:ScanResult){
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppAbove90: $applicationId")
    }

    fun transformForAppBelow90(scanResult:ScanResult){
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppBelow90: $applicationId")
    }

    fun transformForPartyAbove90(scanResult:ScanResult){
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppBelow90: $applicationId")
    }

    fun transformForPartyBelow90(scanResult:ScanResult){
        val applicationId = scanResult.items.get(0).get("applicationId")
        LOG.info("Transforming scanResult transformForAppBelow90: $applicationId")
    }

}