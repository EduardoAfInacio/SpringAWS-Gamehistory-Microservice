package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.producer;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQSProducer {
    private static final String SEND_MESSAGE_QUEUE_NAME = "https://localhost.localstack.cloud:4566/000000000000/game-leaderboard-queue";
    private SqsTemplate sqsTemplate;

    public SQSProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendToLeaderboardQueue(GameStatsResponse response){
        sqsTemplate.send(SEND_MESSAGE_QUEUE_NAME, response);
    }
}
