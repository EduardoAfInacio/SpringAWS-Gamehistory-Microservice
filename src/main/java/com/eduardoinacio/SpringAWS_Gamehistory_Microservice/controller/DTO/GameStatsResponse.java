package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.controller.DTO;

import java.time.Instant;

public record GameStatsResponse(String username, String gameId, Double score, Instant createdAt) { }
