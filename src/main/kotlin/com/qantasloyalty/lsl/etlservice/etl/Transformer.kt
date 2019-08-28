package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class Transformer(
        @Autowired val applicationMapper: ApplicationMapper,
        @Autowired val applicationDataLoader: ApplicationDataLoader) {

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)

    fun transformScan(scanResult: ScanResult) {
        val collect = scanResult.items
                .stream()
                .map(applicationMapper::fromAttributeValuesToApplicationData)
                .collect(Collectors.toList())
        load(collect)
    }

    fun closeDestinationStreams() {
        applicationDataLoader.closeStreamWriters()
    }

    private fun load(applicationDataList: List<ApplicationData>?) {
        println("Calling applicationDataLoader for ${applicationDataList?.size} items")
        /*applicationDataList?.forEach {
            applicationDataLoader.loadApplicationData(it)
        }*/
        applicationDataLoader.loadApplicationDataList(applicationDataList)
    }


}

