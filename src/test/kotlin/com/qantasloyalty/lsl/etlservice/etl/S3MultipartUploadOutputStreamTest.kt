package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.regions.Regions
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

internal class S3MultipartUploadOutputStreamTest {

    @Test
    fun testUploadingFile() {

        val clientRegion = Regions.AP_SOUTHEAST_2
        val bucketName = "avro-file-transfer"
        val keyName = "etl/motor-outgoing-c2-multipart.csv"
        val filePath = "etl-mock-data.csv"

        val outputStream = S3MultipartUploadOutputStream(
                bucketName,
                keyName
        )


        outputStream.use { os ->
            val file = File(filePath)
            file.bufferedReader().lines().forEach {
                os.write(it.toByteArray())
                os.write("\n".toByteArray())
            }
            os.flush()
        }

        println("done")
    }
}