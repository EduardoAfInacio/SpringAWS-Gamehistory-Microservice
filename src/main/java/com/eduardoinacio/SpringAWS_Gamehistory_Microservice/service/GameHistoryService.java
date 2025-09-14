package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.listener.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.NewScoreDTO;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.mapper.GameHistoryMapper;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.producer.SQSProducer;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.apache.coyote.BadRequestException;
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

    public void saveMultipleGames(List<GameStatsRequest> games){
        var gamesEntity = games.stream().map(gameHistoryMapper::toGameHistoryEntity).toList();
        for(GameHistoryEntity game : gamesEntity){
            dynamoDbTemplate.save(game);
        }
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

    public GameStatsResponse getSpecificGameFrom(String username, String gameId) throws BadRequestException {
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

    public void deleteSpecificGameFrom(String username, String gameId) throws BadRequestException {
        var key = Key.builder().partitionValue(username).sortValue(gameId).build();
        var game = dynamoDbTemplate.load(key, GameHistoryEntity.class);
        if(game == null) throw new BadRequestException("Game not found");
        dynamoDbTemplate.delete(game);
    }
}
