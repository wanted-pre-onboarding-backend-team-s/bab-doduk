package com.wanted.babdoduk.infrastructure.webhook.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiscordWebhookClientTest {

    private final static String WEBHOOK_TEST_URL = "https://discord.com/api/webhooks/1169529113174876201/FbWehk12kJ2yGRGaMchd8djNPkuTSt-Rn7IR7geOYK-fxN1utngdaKw2kyteTbdGBwbh";

    @InjectMocks
    private DiscordWebhookClient discordWebhookClient;

    private List<Restaurant> restaurants;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Restaurant restaurant = Restaurant.builder()
            .bizName("bizName")
            .cuisineType("중식")
            .roadAddr("roadAddr")
            .build();

        restaurants = List.of(restaurant);
    }

    @DisplayName("디스코드 웹훅 메세지 POST 전송 - 성공")
    @Test
    public void send_discord_webhook_message_success() {
        // when
        discordWebhookClient.sendRestaurantNoticeMessage(WEBHOOK_TEST_URL, restaurants);

        // then
        assertDoesNotThrow(
            () -> discordWebhookClient.sendRestaurantNoticeMessage(WEBHOOK_TEST_URL, restaurants));
    }

}
