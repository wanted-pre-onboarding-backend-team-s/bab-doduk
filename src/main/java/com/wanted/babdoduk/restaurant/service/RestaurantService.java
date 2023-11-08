package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.common.response.PagedResponse;
import com.wanted.babdoduk.infrastructure.webhook.client.WebhookClient;
import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final String CRON = "0 30 11 * * ?";
    private static final String TIMEZONE = "Asia/Seoul";

    @Value("${webhook.api.url}")
    private String webhookUrl;

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final WebhookClient webhookClient;

    @Transactional(readOnly = true)
    public PagedResponse<RestaurantListResponseDto> getRestaurants(
        RestaurantSearchRequestDto request) {
        return PagedResponse.of(restaurantRepository.findBySearch(request));
    }

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
        List<User> users = userRepository.findUsersByLunchPushApprovedIsTrue();

        users.stream()
            .parallel()
            .forEach(this::sendAsyncWebhookMessage);
    }

    @Async
    public void sendAsyncWebhookMessage(User user) {
        // Webhook URL 을 유저 마다 직접 등록했다는 전제
        // String webhookUrl = user.getWebHookUrl();

        // 현재로서는, 테스트 URL 사용
        String webhookUrl = this.webhookUrl;

        List<Restaurant> recommendedRestaurants =
            restaurantRepository.findRecommendedRestaurants(user);

        webhookClient.sendRestaurantNoticeMessage(webhookUrl, recommendedRestaurants);
    }

}
