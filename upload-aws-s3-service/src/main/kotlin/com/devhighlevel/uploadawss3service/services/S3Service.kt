package com.devhighlevel.uploadawss3service.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.devhighlevel.uploadawss3service.exceptions.BucketException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

@Service
class S3Service(private val s3Client: AmazonS3) {

    val logger = LoggerFactory.getLogger(S3Service::class.java)

    suspend fun upload(file: File, bucketName: String): String {
        val fileKey = file.name
        kotlin.runCatching {
            s3Client.putObject(PutObjectRequest(bucketName, fileKey, file))
            return fileKey
        }.onSuccess {
             file.delete()
        }.onFailure {
            file.delete()
            throw BucketException(it.message)
        }
        return fileKey
    }
}