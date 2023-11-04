package com.wanted.babdoduk.restaurant.service;

import org.springframework.scheduling.annotation.Scheduled;
import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
  
    private static final String CRON = "0 30 11 * * ?";
    private static final String TIMEZONE = "Asia/Seoul";

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = findRestaurant(id);
        restaurant.verifyClosed();

        return restaurant;
    }

    private Restaurant findRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(NotFoundRestaurantException::new);
    }
  
    @Scheduled(cron = CRON, zone = TIMEZONE)
    public void sendDiscordWebHookMessage() {
        // TODO::Discord WebHook 연결 구현
    }

}
