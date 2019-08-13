package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.UploadPartRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import java.io.OutputStream

class S3MultipartUploadOutputStream(
        val bucket: String,
        val objectId: String,
        val maxPartSize: Int = 4096
) : OutputStream() {

    val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain())
            .build()

    val tm = TransferManagerBuilder.standard()
            .withS3Client(s3Client)
            .build()

    val buffer: ByteArray = ByteArray(maxPartSize)
    var index = 0

    override fun write(b: Int) {
        buffer[index] = b.toByte()
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

//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}