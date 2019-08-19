package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.UploadPartRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class S3MultipartUploadOutputStream(
        val name: String,
        val bucket: String,
        val objectId: String,
        val maxPartSize: Int = 4096
) : ByteArrayOutputStream() {


    val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain())
            .build()

    val tm = TransferManagerBuilder.standard()
            .withS3Client(s3Client)
            .build()



    val buffer: ByteArray = ByteArray(maxPartSize)
    var index = 0

    /*override fun write(applicationData: byte[]) {
        buffer[index] = applicationData.toByte()
        index += 1

        if (index == maxPartSize) {
//            buffer.
        }
        println(index)
        //put on buffer

        //if buffer is maxsize (or over)
        //trim to size
        //upload part

        //Clear buffer

    }*/
}