package com.qantasloyalty.lsl.etlservice.etl

import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.qantasloyalty.lsl.etlservice.mapper.ApplicationDataMapper
import com.qantasloyalty.lsl.etlservice.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import java.io.OutputStreamWriter
import java.util.stream.Collectors


@Component
class ApplicationDataLoader @Autowired constructor(private val applicationDataMapper: ApplicationDataMapper) {

    private val bucketName = "avro-file-transfer"
    private val below90AppKeyName = "etl/Car90DayFile-Application.csv"
    private val below90PartyKeyName = "etl/Car90DayFile-Party.csv"
    private val above90AppKeyName = "etl/Car-Application.csv"
    private val above90PartyKeyName = "etl/Car-Party.csv"

    private val above90AppStream = S3MultipartUploadBufferedOutputStream("above90App", bucketName, above90AppKeyName)
    private val above90PartyStream = S3MultipartUploadBufferedOutputStream("above90Party", bucketName, above90PartyKeyName)
    private val below90AppStream = S3MultipartUploadBufferedOutputStream("below90App", bucketName, below90AppKeyName)
    private val below90PartyStream = S3MultipartUploadBufferedOutputStream("below90Party", bucketName, below90PartyKeyName)

    var above90AppStreamWriter = OutputStreamWriter(above90AppStream)
    var above90PartyStreamWriter = OutputStreamWriter(above90PartyStream)
    var below90AppStreamWriter = OutputStreamWriter(below90AppStream)
    var below90PartyStreamWriter = OutputStreamWriter(below90PartyStream)

    fun loadApplicationData(applicationData: ApplicationData) {
        //TODO Make this parallel? Use coroutines or channels?
        below90AppStream.write(applicationData.toBelow90AppCsvString().toByteArray())
        above90AppStream.write(applicationData.toAbove90AppCsvString().toByteArray())
        if (!CollectionUtils.isEmpty(applicationData.attributes?.parties)) {
            below90PartyStream.write(applicationData.toBelow90PartyCsvString().toByteArray())
            above90PartyStream.write(applicationData.toAbove90PartyCsvString().toByteArray())
        }
    }

    fun closeStreamWriters() {
        above90AppStream.close()
        above90PartyStream.close()
        below90AppStream.close()
        below90PartyStream.close()
    }

    fun loadApplicationDataList(applicationDataList: List<ApplicationData>?) {
        val applicationAbove90DataList: MutableList<ApplicationAbove90Data>? = applicationDataList?.stream()?.map(applicationDataMapper::toAppAbove90)?.collect(Collectors.toList())
        val applicationBelow90DataList: MutableList<ApplicationBelow90Data>? = applicationDataList?.stream()?.map(applicationDataMapper::toAppBelow90)?.collect(Collectors.toList())
        StatefulBeanToCsvBuilder<ApplicationAbove90Data>(above90AppStreamWriter).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build().write(applicationAbove90DataList)
        StatefulBeanToCsvBuilder<ApplicationBelow90Data>(below90AppStreamWriter).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build().write(applicationBelow90DataList)

        val collectAbove90: MutableList<List<PartyAbove90Data>>? = applicationDataList?.stream()?.map(applicationDataMapper::toPartyAbove90)?.collect(Collectors.toList())
        val above90PartyList: List<PartyAbove90Data>? = collectAbove90?.filterNotNull()?.flatten()

        val collectBelow90: MutableList<List<PartyBelow90Data>>? = applicationDataList?.stream()?.map(applicationDataMapper::toPartyBelow90)?.collect(Collectors.toList())
        val below90PartyList: List<PartyBelow90Data>? = collectBelow90?.filterNotNull()?.flatten()

        StatefulBeanToCsvBuilder<PartyAbove90Data>(above90PartyStreamWriter).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build().write(above90PartyList)
        StatefulBeanToCsvBuilder<PartyBelow90Data>(below90PartyStreamWriter).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build().write(below90PartyList)

    }

}
