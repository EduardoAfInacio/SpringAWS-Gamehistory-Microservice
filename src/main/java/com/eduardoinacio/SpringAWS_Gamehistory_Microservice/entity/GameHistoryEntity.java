package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

@DynamoDbBean
public class GameHistoryEntity {
    private String username;
    private String gameId;
    private Double score;
    private Instant createdAt;

    public GameHistoryEntity() {
    }

    public GameHistoryEntity(String username, String gameId, Double score, Instant createdAt) {
        this.username = username;
        this.gameId = gameId;
        this.score = score;
        this.createdAt = createdAt;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "game_id")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDbAttribute(value = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @DynamoDbAttribute(value = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
