package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PartETag
import com.amazonaws.services.s3.model.UploadPartRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.s3.transfer.Upload
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.ArrayList
import kotlin.text.Typography.tm
import com.amazonaws.services.s3.model.UploadPartResult
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest





class S3MultipartUploadOutputStream(
        val bucket: String,
        val objectId: String,
        val maxPartSize: Int = 4096
) : OutputStream() {


    val s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain())
            .build()

//    val tm = TransferManagerBuilder.standard()
//            .withS3Client(s3Client)
//            .build()

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
        if (multipartIndex == 0) {
            buffer = ByteArray(maxPartSize)
        }
        buffer[multipartIndex] = b.toByte()

        if (multipartIndex == maxPartSize - 1) {
            uploadPart()
            multipartIndex = 0
            multipartNumber += 1

        } else {
            multipartIndex += 1
        }
        fileIndex += 1
    }

    override fun flush() {
        uploadPart()
    }

    override fun close() {
        val compRequest = CompleteMultipartUploadRequest(bucket, objectId, initResponse.uploadId, partETags)
        s3Client.completeMultipartUpload(compRequest)
    }

    private fun uploadPart() {
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
                .withFileOffset((fileIndex - multipartIndex).toLong())
//                .withObjectMetadata(objectMetaData)

        val uploadResult = s3Client.uploadPart(uploadRequest)
        partETags.add(uploadResult.partETag)
//        val upload = tm.upload(bucket, objectId, ByteArrayInputStream(buffer), ObjectMetadata().apply {
//            contentLength = multipartIndex.toLong()
//        })

//        upload.waitForUploadResult()
        // Upload the part and add the response's ETag to our list.
//        val uploadResult = s3Client.uploadPart(uploadRequest)

//        partETags.add(uploadResult.partETag)
    }
}