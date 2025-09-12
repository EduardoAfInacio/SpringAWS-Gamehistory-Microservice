package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service.GameHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/games")
public class GameHistoryController {
    private GameHistoryService gameHistoryService;

    public GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody GameStatsRequest request) {
        gameHistoryService.saveGameInfos(request);
        return ResponseEntity.ok().build();
    }
}
