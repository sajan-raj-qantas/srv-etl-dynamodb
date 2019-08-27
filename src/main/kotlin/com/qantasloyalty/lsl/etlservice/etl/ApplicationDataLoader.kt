package com.qantasloyalty.lsl.etlservice.etl

import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import org.apache.commons.collections4.CollectionUtils
import org.springframework.stereotype.Component

@Component
class ApplicationDataLoader {

    private val bucketName = "avro-file-transfer"
    private val below90AppKeyName  = "etl/Car90DayFile-Application.csv"
    private val below90PartyKeyName = "etl/Car90DayFile-Party.csv"
    private val above90AppKeyName = "etl/Car-Application.csv"
    private val above90PartyKeyName = "etl/Car-Party.csv"

    private val above90AppStream = S3MultipartUploadBufferedOutputStream(ApplicationData.above90AppHeading(),"above90App", bucketName, above90AppKeyName)
    private val above90PartyStream = S3MultipartUploadBufferedOutputStream(ApplicationData.above90PartyHeading(),"above90Party", bucketName, above90PartyKeyName)
    private val below90AppStream = S3MultipartUploadBufferedOutputStream(ApplicationData.below90AppHeading(),"below90App", bucketName, below90AppKeyName)
    private val below90PartyStream = S3MultipartUploadBufferedOutputStream(ApplicationData.below90PartyHeading(),"below90Party", bucketName, below90PartyKeyName)

    fun loadApplicationData(applicationData: ApplicationData) {
        //TODO Make this parallel? Use coroutines or channels?
        below90AppStream.write(applicationData.toBelow90AppCsvString().toByteArray())
        above90AppStream.write(applicationData.toAbove90AppCsvString().toByteArray())
        if(!CollectionUtils.isEmpty(applicationData.attributes?.parties)) {
            below90PartyStream.write(applicationData.toBelow90PartyCsvString().toByteArray())
            above90PartyStream.write(applicationData.toAbove90PartyCsvString().toByteArray())
        }
    }

    fun closeStreams() {
        above90AppStream.close()
        above90PartyStream.close()
        below90AppStream.close()
        below90PartyStream.close()
    }


}
