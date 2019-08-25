package com.qantasloyalty.lsl.etlservice.etl

import com.qantasloyalty.lsl.etlservice.model.ApplicationDataNew
import org.springframework.stereotype.Component

@Component
class ApplicationDataLoader {

    private val bucketName = "avro-file-transfer"
    private val above90AppKeyName = "etl/Car90DayFile-Application.csv"
    private val above90PartyKeyName = "etl/Car90DayFile-Party.csv"
    private val below90AppKeyName = "etl/Car-Application.csv"
    private val below90PartykeyName = "etl/Car-Party.csv"

    private val above90AppStream = S3MultipartUploadBufferedOutputStream("above90App", bucketName, above90AppKeyName)
    private val above90PartyStream = S3MultipartUploadBufferedOutputStream("above90Party", bucketName, above90PartyKeyName)
    private val below90AppStream = S3MultipartUploadBufferedOutputStream("below90App", bucketName, below90AppKeyName)
    private val below90PartyStream = S3MultipartUploadBufferedOutputStream("below90Party", bucketName, below90PartykeyName)

    fun loadApplicationData(applicationDataNew: ApplicationDataNew) {
        //TODO Make this parallel? Use coroutines or channels?
        below90AppStream.write(applicationDataNew.toBelow90AppCsvString().toByteArray())
        below90PartyStream.write(applicationDataNew.toBelow90PartyCsvString().toByteArray())
        above90AppStream.write(applicationDataNew.toAbove90AppCsvString().toByteArray())
        above90PartyStream.write(applicationDataNew.toAbove90AppCsvString().toByteArray())
    }

    fun closeStreams() {
        above90AppStream.close()
        above90PartyStream.close()
        below90AppStream.close()
        below90PartyStream.close()
    }


}
