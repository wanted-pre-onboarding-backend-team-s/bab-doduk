package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.common.response.PagedResponse;
import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final String CRON = "0 30 11 * * ?";
    private static final String TIMEZONE = "Asia/Seoul";

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public PagedResponse<RestaurantListResponseDto> getRestaurants(RestaurantSearchRequestDto request) {
        return PagedResponse.of(restaurantRepository.findBySearch(request));
    }

    @Cacheable(value = "restaurant", key = "#id")
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
