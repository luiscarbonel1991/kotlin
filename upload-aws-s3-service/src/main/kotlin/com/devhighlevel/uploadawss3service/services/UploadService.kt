package com.devhighlevel.uploadawss3service.services

import com.devhighlevel.uploadawss3service.controller.UploadController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.collect
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Path
import java.util.*

@Service
class UploadService(private val s3Service: S3Service) {

    val logger: Logger = LoggerFactory.getLogger(UploadService::class.java)

    @Value("\${aws.s3.buckets.twitch}")
    private val bucket: String = ""

    @Value("\${paths.base}")
    private val basePath: String = ""

    suspend fun upload(files: Flow<FilePart>) {
        files.map {
            val path = Path.of(basePath).resolve("${UUID.randomUUID()}.png")
            it.transferTo(path).collect {}
            path
        }.collect {
            s3Service.upload(File(it.toUri()), bucket)
        }
    }
}