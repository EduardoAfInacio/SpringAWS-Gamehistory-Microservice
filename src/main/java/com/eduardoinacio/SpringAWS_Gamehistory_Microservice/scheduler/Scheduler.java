package com.eduardoinacio.SpringAWS_Gamehistory_Microservice.scheduler;

import com.eduardoinacio.SpringAWS_Gamehistory_Microservice.service.LeaderboardService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Scheduler {
    private LeaderboardService leaderboardService;

    public Scheduler(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public void analyseTopTenScores(){
        leaderboardService.updateTopTenLeaderboard();
    }
}
