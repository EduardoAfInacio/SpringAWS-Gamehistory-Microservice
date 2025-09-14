package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AWSConfig {
    private static final String ENDPOINT_OVERRIDE = "http://localhost:4566";

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(ENDPOINT_OVERRIDE))
                .region(Region.SA_EAST_1)
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient(){
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(ENDPOINT_OVERRIDE))
                .region(Region.SA_EAST_1)
                .build();
    }

    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .endpointOverride(URI.create(ENDPOINT_OVERRIDE))
                .region(Region.SA_EAST_1)
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
