package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.NewScoreDTO;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.mapper.GameHistoryMapper;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    public GameStatsResponse getSpecificGameFrom(String gameId, String username) throws BadRequestException {
        var key = Key.builder().partitionValue(username).sortValue(gameId).build();
        var game = dynamoDbTemplate.load(key, GameHistoryEntity.class);
        if(game == null) throw new BadRequestException("Game not found");
        return gameHistoryMapper.toGameStatsResponse(game);
    }

    public void updateSpecificGameFrom(String gameId, String username, NewScoreDTO request) throws BadRequestException {
        var key = Key.builder().partitionValue(username).sortValue(gameId).build();
        var game = dynamoDbTemplate.load(key, GameHistoryEntity.class);
        if(game == null) throw new BadRequestException("Game not found");
        game.setScore(request.score());
        dynamoDbTemplate.save(game);
    }
}
