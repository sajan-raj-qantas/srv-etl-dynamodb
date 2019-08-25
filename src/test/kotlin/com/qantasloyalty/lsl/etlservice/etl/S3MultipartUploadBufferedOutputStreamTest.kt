package com.qantasloyalty.lsl.etlservice.etl

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

internal class S3MultipartUploadBufferedOutputStreamTest {
    val fileName = "mock-data-100000.csv"

    @Before
    fun generateTestFile(){
        generateCsvFile(fileName,300000)
    }

    @After
    fun deleteTestFile(){
       var testFile = File(fileName)
        if(testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun testUploadingFile() {
        println("Started Test")
        val bucketName = "avro-file-transfer"
        val keyName = "etl/multipart-test.csv"

        val outputStream = S3MultipartUploadBufferedOutputStream("test",
                bucketName,
                keyName
        )
        println("Created OutputStream from Test file")
        outputStream.use { os ->
            val file = File(fileName)
            file.bufferedReader().lines().forEach {
                os.write(it.toByteArray())
                os.write("\n".toByteArray())
            }
            os.flush()
        }
        println("Finished Test")
    }


    fun generateCsvFile(fileName:String, rows:Int){
        println("Generating test file with $rows rows...")
        var file = File(fileName)
        file.writeText("applicationId,carRego,carColor,carUse,carMake,carModel\n")
        repeat(rows){
            file.appendText("APP$it,ABC$it,BLUE$it,PRIVATE$it,MAKE$it,MODEL$it\n")
        }
        println("Generated test file with size ${file.length()}")
    }
}