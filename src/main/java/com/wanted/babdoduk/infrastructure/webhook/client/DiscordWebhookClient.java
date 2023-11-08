package com.wanted.babdoduk.infrastructure.webhook.client;

import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.AUTHOR;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.AVATAR_URL;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.CONTENT;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DEFAULT_AVATAR_URL;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DEFAULT_CONTENT;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DEFAULT_DESCRIPTION;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DEFAULT_TITLE;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DEFAULT_USERNAME;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.DESCRIPTION;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.EMBEDS;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.FIELDS;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.ICON_URL;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.NAME;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.NOT_FOUND_RESTAURANT_DESCRIPTION;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.SEND_SUCCESS_LOG;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.USERNAME;
import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.VALUE;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class DiscordWebhookClient implements WebhookClient {

    @Override
    public void sendRestaurantNoticeMessage(String webhookUrl, List<Restaurant> restaurants) {
        Map<String, Object> discordMessageBodyMap = createDiscordMessage(restaurants);

        WebClient baseWebClient = getBaseWebClient();
        postRequest(webhookUrl, discordMessageBodyMap, baseWebClient);
        log.info(SEND_SUCCESS_LOG);
    }

    private Map<String, Object> createDiscordMessage(List<Restaurant> restaurants) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put(USERNAME, DEFAULT_USERNAME);
        contentMap.put(AVATAR_URL, DEFAULT_AVATAR_URL);
        contentMap.put(CONTENT, DEFAULT_CONTENT);

        List<Map<String, Object>> embeds = createEmbeds(restaurants);
        contentMap.put(EMBEDS, embeds);

        return contentMap;
    }

    private List<Map<String, Object>> createEmbeds(List<Restaurant> restaurants) {
        List<Map<String, Object>> embeds = new ArrayList<>();
        Map<String, Object> embed = new HashMap<>();
        List<Map<String, Object>> fields = new ArrayList<>();

        embed.put(AUTHOR, createAuthorMap());
        embed.put(DESCRIPTION, createDescription(restaurants.size()));

        for (Restaurant restaurant : restaurants) {
            fields.add(createFieldMap(restaurant));
        }

        embed.put(FIELDS, fields);
        embeds.add(embed);
        return embeds;
    }

    private String createDescription(int restaurantListSize) {
        if (restaurantListSize == 0) {
            return NOT_FOUND_RESTAURANT_DESCRIPTION;
        }
        return DEFAULT_DESCRIPTION;
    }

    private Map<String, Object> createAuthorMap() {
        return Map.of(NAME, DEFAULT_TITLE, ICON_URL, DEFAULT_AVATAR_URL);
    }

    private Map<String, Object> createFieldMap(Restaurant restaurant) {
        StringBuilder stringBuilder = new StringBuilder();
        String cuisineType = restaurant.getCuisineType();
        String bizName = restaurant.getBizName();
        String roadAddr = restaurant.getRoadAddr();

        stringBuilder
            .append("[")
            .append(cuisineType)
            .append("] ")
            .append(bizName);

        return Map.of(NAME, stringBuilder.toString(), VALUE, roadAddr);
    }

    private WebClient getBaseWebClient() {
        return WebClient.builder()
            .build();
    }

    private <T> void postRequest(String webhookUrl, Map<String, Object> bodyMap,
        WebClient webClient) {
        webClient
            .post()
            .uri(webhookUrl)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
