package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.mapper;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.entity.GameHistoryEntity;

public class GameHistoryMapper {
    public GameHistoryEntity toGameHistoryEntity(GameStatsRequest request){
        return new GameHistoryEntity(
                request.username(),
                request.gameId(),
                request.score(),
                request.createdAt()
        );
    }

    public GameStatsResponse toGameStatsResponse(GameHistoryEntity entity){
        return new GameStatsResponse(
                entity.getUsername(),
                entity.getGameId(),
                entity.getScore(),
                entity.getCreatedAt()
        );
    }
}
