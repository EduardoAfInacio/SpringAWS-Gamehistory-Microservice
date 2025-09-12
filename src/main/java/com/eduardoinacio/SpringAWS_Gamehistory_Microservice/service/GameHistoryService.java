package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GameHistoryService {
    private DynamoDbTemplate dynamoDbTemplate;

    public GameHistoryService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public void saveGameInfos(GameStatsRequest request) {
        GameHistoryEntity game = request.toGameHistoryEntity();
        dynamoDbTemplate.save(game);
    }
}
