package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsRequest;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO.GameStatsResponse;
import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service.GameHistoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GameHistoryController {
    private GameHistoryService gameHistoryService;

    public GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @PostMapping("/games")
    public ResponseEntity<Void> save(@RequestBody GameStatsRequest request) {
        gameHistoryService.saveGameInfos(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/games/{gameId}")
    public ResponseEntity<GameStatsResponse> getGamesFrom(@PathVariable(value = "username") String username,
                                                          @PathVariable(value = "gameId") String gameId)
                                                          throws BadRequestException {
        var userGame = gameHistoryService.getSpecificGameFrom(gameId, username);
        return ResponseEntity.ok().body(userGame);
    }
}
