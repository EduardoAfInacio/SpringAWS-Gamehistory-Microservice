package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.mapper.GameHistoryMapper;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Service
public class GameHistoryService {
    private DynamoDbTemplate dynamoDbTemplate;
    private GameHistoryMapper gameHistoryMapper;

    public GameHistoryService(DynamoDbTemplate dynamoDbTemplate, GameHistoryMapper gameHistoryMapper) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.gameHistoryMapper = gameHistoryMapper;
    }

    public void saveGameInfos(GameStatsRequest request) {
        GameHistoryEntity game = gameHistoryMapper.toGameHistoryEntity(request);
        dynamoDbTemplate.save(game);
    }

    public List<GameStatsResponse> getGameHistoryFrom(String username){
        var key = Key.builder().partitionValue(username).build();
        var condition = QueryConditional.keyEqualTo(key);
        var query = QueryEnhancedRequest.builder().queryConditional(condition).build();
        var queryResult = dynamoDbTemplate.query(query, GameHistoryEntity.class).items().stream().toList();
        var gameList = queryResult.stream().map(gameHistoryMapper::toGameStatsResponse).toList();
        return gameList;
    }
}
