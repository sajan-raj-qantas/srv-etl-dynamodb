package com.qantasloyalty.lsl.etlservice.etl

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest
import com.amazonaws.services.s3.model.PartETag
import com.amazonaws.services.s3.model.UploadPartRequest
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.qantasloyalty.lsl.etlservice.model.ApplicationData
import java.io.ByteArrayOutputStream
import sun.security.krb5.Confounder.bytes
import java.io.ByteArrayInputStream
import java.util.ArrayList


data class ApplicationDataUploadOutputStream(val name: String, val maxPartSize: Int) {
    //Pass s3 details here?
    val buffer = ByteArrayOutputStream(maxPartSize)
    var itemsInBuffer: Int = 0;
    val clientRegion = Regions.AP_SOUTHEAST_2
    val bucketName = "avro-file-transfer"
    val keyName = "etl/motor-outgoing-c2-multipart.csv"
    val filePath = "etl-mock-data.csv"
    var partSize = (5 * 1024 * 1024).toLong() // Set part size to 5 MB.
    val partETags = ArrayList<PartETag>()
    val s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(clientRegion)
            .withCredentials(ProfileCredentialsProvider())
            .build()
    val initedUpload:Boolean =false
    val initRequest = InitiateMultipartUploadRequest(bucketName, keyName)
    val initResponse = s3Client.initiateMultipartUpload(initRequest)

    // Upload the file parts.
    var filePosition: Long = 0
    var partNumber: Int =1

    //TODO upload to S3
    val tm = TransferManagerBuilder.standard()
            .withS3Client(null)
            .withMultipartUploadThreshold((5 * 1024 * 1025).toLong())
            .build()

    @Synchronized
    fun pushToBuffer(applicationData: ApplicationData) {
        if ( itemsInBuffer >= maxPartSize ) {
            upload()
            emptyBuffer()
        }else {
            push(applicationData)
        }
    }

    @Synchronized
    private fun push(applicationData: ApplicationData) {
        buffer.write((applicationData.toCsvString()+"\n").toByteArray())
        itemsInBuffer++
    }

    @Synchronized
    fun emptyBuffer() {
        buffer.reset()
        println("Cleared contents of Buffer $name..")
    }

    @Synchronized
    fun upload() {
        println("Uploading contents of Buffer $name..")
       /* if(!initedUpload){
             initRequest = InitiateMultipartUploadRequest(bucketName, keyName)
             initResponse = s3Client.initiateMultipartUpload(initRequest)
        }*/
        val inputStream = ByteArrayInputStream(buffer.toByteArray())

                // Because the last part could be less than 5 MB, adjust the part size as needed.
               // partSize = Math.min(partSize, contentLength - filePosition)

                // Create the request to upload a part.
                val uploadRequest = UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(keyName)
                        .withUploadId(initResponse.uploadId)
                        .withPartNumber(partNumber)
                        .withFileOffset(filePosition)
                        .withInputStream(inputStream)
                        .withPartSize(partSize)


                // Upload the part and add the response's ETag to our list.
                val uploadResult = s3Client.uploadPart(uploadRequest)
                partETags.add(uploadResult.partETag)

                filePosition += partSize
                partNumber++

        // Complete the multipart upload.
        val compRequest = CompleteMultipartUploadRequest(bucketName, keyName,
                initResponse.uploadId, partETags)
        s3Client.completeMultipartUpload(compRequest)

    }
}