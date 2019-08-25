package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.services.dynamodbv2.model.ScanResult
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationMapper
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataKey
import com.qantasloyalty.lsl.etlservice.model.ApplicationDataNew
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class Transformer(
        @Autowired val applicationMapper: ApplicationMapper,
        @Autowired val applicationDataLoader: ApplicationDataLoader,
        @Autowired val batchRetriever: BatchRetriever) {

    private val LOG = LoggerFactory.getLogger(object {}::class.java.`package`.name)

    fun transformScan(scanResult: ScanResult) {
        //Collect all Application table data for items in ScanResult
        val applicationList = scanResult.items.stream()
                .map(applicationMapper::fromAttributeValuesToApplication)
                .collect(Collectors.toList())
        //println("Application List :: $applicationList")
        //Collect all ids
        val applicationDataKeys = applicationList.stream()
                .map { ApplicationDataKey(it.applicationId, it.lastDataCreatedTimestamp) }
                .collect(Collectors.toList())

        val applicationDataList: List<ApplicationDataNew>? = batchRetriever.batchGetItems(applicationDataKeys)
        //println("ApplicationDataList: $applicationDataList")
        load(applicationDataList)
    }

    fun closeDestinationStreams(){
        applicationDataLoader.closeStreams()
    }

    private fun load(applicationDataList: List<ApplicationDataNew>?) {
        println("Calling applicationDataLoader for ${applicationDataList?.size} items")
        applicationDataList?.forEach {
            applicationDataLoader.loadApplicationData(it)
        }
    }


}

