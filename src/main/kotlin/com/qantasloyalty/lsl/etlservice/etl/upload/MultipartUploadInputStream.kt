package com.qantasloyalty.lsl.etlservice.etl.upload


import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.*
import java.io.*

import java.util.ArrayList
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.s3.transfer.TransferManager



object MultipartUploadInputStream {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val clientRegion = Regions.AP_SOUTHEAST_2
        val bucketName = "avro-file-transfer"
        val keyName = "etl/test-multipart.csv"

        var outputStream = ByteArrayOutputStream()
        var inputStream = ByteArrayInputStream(outputStream.toByteArray())

        for (i in 0..100) {
            outputStream.write("hello $i \n".toByteArray())
        }
        // val contentLength = file.length()
        var partSize = (5 * 1024 * 1024).toLong() // Set part size to 5 MB.

        try {
            val s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(ProfileCredentialsProvider())
                    .build()

            val tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build()

            // Create a list of ETag objects. You retrieve ETags for each object part uploaded,
            // then, after each individual part has been uploaded, pass the list of ETags to
            // the request to complete the upload.
            val partETags = ArrayList<PartETag>()

            // Initiate the multipart upload.
            val initRequest = InitiateMultipartUploadRequest(bucketName, keyName)
            val initResponse = s3Client.initiateMultipartUpload(initRequest)

            // Upload the file parts.
            var filePosition: Long = 0
            var i = 1
            // Because the last part could be less than 5 MB, adjust the part size as needed.
            // partSize = Math.min(partSize, contentLength - filePosition)
            inputStream = ByteArrayInputStream(outputStream.toByteArray())
            //inputStream.close()
            while (inputStream.available() > 0) {
                var objectMetaData = ObjectMetadata()
               // objectMetaData.contentLength = outputStream.size().toLong()
                objectMetaData.contentLength = 100
                // Create the request to upload a part.
                val uploadRequest = UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(keyName)
                        .withUploadId(initResponse.uploadId)
                        .withPartNumber(i)
                        .withInputStream(inputStream)
                        .withPartSize(32)
                        .withObjectMetadata(objectMetaData)

                tm.upload(bucketName,keyName,inputStream,objectMetaData)

                // Upload the part and add the response's ETag to our list.
                val uploadResult = s3Client.uploadPart(uploadRequest)

                partETags.add(uploadResult.partETag)

                filePosition += partSize
                i++

                // Complete the multipart upload.
                val compRequest = CompleteMultipartUploadRequest(bucketName, keyName,
                        initResponse.uploadId, partETags)
                s3Client.completeMultipartUpload(compRequest)
            }
        } catch (e: AmazonServiceException) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace()
        } catch (e: SdkClientException) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace()
        } finally {
            inputStream.close()
        }

    }
}

