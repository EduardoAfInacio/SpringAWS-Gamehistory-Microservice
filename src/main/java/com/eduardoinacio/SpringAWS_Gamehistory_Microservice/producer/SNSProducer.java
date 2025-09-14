package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.producer;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
public class SNSProducer {
    private static final String TOPIC_ARN = "arn:aws:sns:sa-east-1:000000000000:top10";
    private SnsAsyncClient snsAsyncClient;

    public SNSProducer(SnsAsyncClient snsAsyncClient) {
        this.snsAsyncClient = snsAsyncClient;
    }

    public void newTopTenNotify(){
        snsAsyncClient.publish(PublishRequest.builder()
                .topicArn(TOPIC_ARN)
                .message("New top 10 games available!")
                .build());
    }
}
