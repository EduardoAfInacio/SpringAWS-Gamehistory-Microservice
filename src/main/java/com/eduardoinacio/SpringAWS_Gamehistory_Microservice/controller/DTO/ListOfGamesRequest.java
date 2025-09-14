package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.listener.GameStatsRequest;

import java.util.List;

public record ListOfGamesRequest(List<GameStatsRequest> games) {
}
