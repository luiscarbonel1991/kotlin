package com.devhighlevel.uploadawss3service.controller

import com.devhighlevel.uploadawss3service.services.UploadService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/upload/api")
class UploadController (private val uploadService: UploadService) {

    @RequestMapping("/health")
    suspend fun health(): ResponseEntity<String> {
        return ResponseEntity("OK", HttpStatus.OK)
    }


    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    suspend fun upload(@RequestPart files: Flux<FilePart>): ResponseEntity<String> {
        uploadService.upload(files.asFlow())
        return ResponseEntity("OK", HttpStatus.OK)
    }

}