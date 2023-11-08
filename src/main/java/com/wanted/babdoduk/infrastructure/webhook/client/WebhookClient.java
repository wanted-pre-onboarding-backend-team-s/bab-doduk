package com.wanted.babdoduk.infrastructure.webhook.client;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import java.util.List;

public interface WebhookClient {

    void sendRestaurantNoticeMessage(List<Restaurant> restaurants);

}
