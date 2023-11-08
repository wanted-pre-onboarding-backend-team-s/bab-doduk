package com.wanted.babdoduk.infrastructure.webhook.client;

import static com.wanted.babdoduk.infrastructure.webhook.constants.WebhookConstants.*;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class DiscordWebhookClient implements WebhookClient {

    @Value("${webhook.api.url}")
    private String discordWebhookUrl;

    @Override
    public void sendRestaurantNoticeMessage(List<Restaurant> restaurants) {
        Map<String, Object> discordMessageBodyMap = createDiscordMessage(restaurants);

        WebClient baseWebClient = getBaseWebClient();
        postRequest(discordMessageBodyMap, baseWebClient);
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
        embed.put(DESCRIPTION, DEFAULT_DESCRIPTION);

        for (Restaurant restaurant : restaurants) {
            fields.add(createFieldMap(restaurant));
        }

        embed.put(FIELDS, fields);
        embeds.add(embed);
        return embeds;
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

    private <T> void postRequest(Map<String, Object> bodyMap,
        WebClient webClient) {
        webClient
            .post()
            .uri(discordWebhookUrl)
            .bodyValue(bodyMap)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
