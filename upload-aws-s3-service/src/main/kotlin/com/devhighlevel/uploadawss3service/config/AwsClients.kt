package com.devhighlevel.uploadawss3service.config

import com.amazonaws.auth.AWSSessionCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsClients {

    @Value("\${aws.s3.accessKey}")
    private val ACCESS_KEY: String = ""

    @Value("\${aws.s3.secretKey}")
    private val SECRET_KEY: String= ""

    @Value("\${aws.s3.region}")
    private val REGION: String= ""


    @Bean
    fun s3Client(): AmazonS3? {
       val credentials = BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)
       return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(URL, REGION))
            .withPathStyleAccessEnabled(true)
           .build()

    }


    companion object {
        const val URL: String = "http://localhost:4566"
    }
}