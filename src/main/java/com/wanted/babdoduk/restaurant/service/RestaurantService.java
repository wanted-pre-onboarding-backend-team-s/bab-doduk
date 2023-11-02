package com.wanted.babdoduk.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private static final String CRON = "0 30 11 * * ?";
    private static final String TIMEZONE = "Asia/Seoul";

    @Scheduled(cron = CRON, zone = TIMEZONE)
    public void sendDiscordWebHookMessage() {
        // TODO::Discord WebHook 연결 구현
    }

}
