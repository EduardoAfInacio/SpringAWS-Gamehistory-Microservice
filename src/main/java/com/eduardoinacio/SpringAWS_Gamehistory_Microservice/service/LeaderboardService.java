package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.mapper.GameHistoryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import io.awspring.cloud.s3.S3Template;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

@Service
public class LeaderboardService {
    private DynamoDbTemplate dynamoDbTemplate;
    private GameHistoryMapper gameHistoryMapper;
    private ObjectMapper objectMapper;
    private S3Template s3Template;

    public LeaderboardService(DynamoDbTemplate dynamoDbTemplate, GameHistoryMapper gameHistoryMapper, ObjectMapper objectMapper, S3Template s3Template) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.gameHistoryMapper = gameHistoryMapper;
        this.objectMapper = objectMapper;
        this.s3Template = s3Template;
    }

    @Async
    public CompletableFuture<Void> updateTopTenLeaderboard() {
        var scanRequest = ScanEnhancedRequest.builder().build();
        var top10 = dynamoDbTemplate.scan(scanRequest, GameHistoryEntity.class)
                .items()
                .stream()
                .sorted(Comparator.comparingDouble(GameHistoryEntity::getScore).reversed())
                .limit(10)
                .map(gameHistoryMapper::toGameStatsResponse)
                .toList();

        try{
            String json = objectMapper.writeValueAsString(top10);

            s3Template.store("leaderboard-bucket", "top10.json", json);
            return CompletableFuture.completedFuture(null);
        }catch (Exception e){
            throw new RuntimeException("Error while serializing top10 leaderboard");
        }
    }
}
