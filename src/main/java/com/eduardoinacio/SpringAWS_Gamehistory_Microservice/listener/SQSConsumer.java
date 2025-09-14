package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.listener;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service.GameHistoryService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SQSConsumer {
    private GameHistoryService gameHistoryService;

    public SQSConsumer(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @SqsListener("game-server-queue")
    public void receiveMessage(GameStatsRequest request){
        gameHistoryService.saveGameInfos(request);
    }
}
