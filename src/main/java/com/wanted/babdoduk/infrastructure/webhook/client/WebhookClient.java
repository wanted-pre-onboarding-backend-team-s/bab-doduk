package com.wanted.babdoduk.infrastructure.webhook.client;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.util.List;

public interface WebhookClient {

    void sendRestaurantNoticeMessage(String webhookUrl, List<Restaurant> restaurants);

}
