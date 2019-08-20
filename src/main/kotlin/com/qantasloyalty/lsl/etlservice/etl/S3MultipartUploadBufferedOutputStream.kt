package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest
import com.amazonaws.services.s3.model.PartETag
import com.amazonaws.services.s3.model.UploadPartRequest
import java.io.ByteArrayInputStream
import java.io.OutputStream
import java.util.ArrayList
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest

class S3MultipartUploadBufferedOutputStream(
        val bucket: String,
        val objectId: String,
        val maxPartSize: Int = 1 * 1024 * 1024 + 1 // 5 MB
) : OutputStream() {

    val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain())
            .build()

    val partETags = ArrayList<PartETag>()

    // Initiate the multipart upload.
    val initRequest = InitiateMultipartUploadRequest(bucket, objectId)
    val initResponse = s3Client.initiateMultipartUpload(initRequest)

    lateinit var buffer: ByteArray
    var multipartNumber = 0
    var multipartIndex = 0
    var fileIndex = 0

    @Synchronized
    override fun write(b: Int) {
       // println("###Writing###")
        if (multipartIndex == 0) {
            buffer = ByteArray(maxPartSize)
        }
        buffer[multipartIndex] = b.toByte()

        if (multipartIndex == maxPartSize - 1) {
            println("Uploading part...")
            uploadPart()
        } else {
            multipartIndex += 1
        }
        fileIndex += 1
    }

    override fun flush() {
        uploadPart()
    }

    override fun close() {
        if(multipartIndex!=0){
            uploadPart()
        }
        val compRequest = CompleteMultipartUploadRequest(bucket, objectId, initResponse.uploadId, partETags)
        s3Client.completeMultipartUpload(compRequest)
    }

    private fun uploadPart() {
        //println("Uploading part ${multipartNumber + 1}...")
        if (multipartIndex == 0) {
            return
        }

        val uploadRequest = UploadPartRequest()
                .withBucketName(bucket)
                .withKey(objectId)
                .withUploadId(initResponse.uploadId)
                .withPartNumber(multipartNumber + 1)
                .withInputStream(ByteArrayInputStream(buffer, 0, multipartIndex))
                .withPartSize(multipartIndex.toLong())

        val uploadResult = s3Client.uploadPart(uploadRequest)
        println("Uploaded part ${multipartNumber + 1}")
        partETags.add(uploadResult.partETag)

        multipartIndex = 0
        multipartNumber += 1
    }
}