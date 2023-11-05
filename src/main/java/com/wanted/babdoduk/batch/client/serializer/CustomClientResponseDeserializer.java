package com.wanted.babdoduk.batch.client.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.babdoduk.batch.client.response.success.ClientResponse;
import com.wanted.babdoduk.batch.client.response.success.Head;
import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import com.wanted.babdoduk.batch.client.response.common.ClientResponseStatus;
import java.io.IOException;
import java.util.List;

public class CustomClientResponseDeserializer extends JsonDeserializer<ClientResponse> {

    private final ObjectMapper objectMapper;

    public CustomClientResponseDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ClientResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode node = objectCodec.readTree(p);
        int totalCount = Integer.parseInt(
                node.get("GENRESTRT").get(0).get("head").get(0).get("list_total_count").asText());
        String apiVersion = node.get("GENRESTRT").get(0).get("head").get(2).get("api_version").asText();
        JsonNode rowsNode = node.get("GENRESTRT").get(1).get("row");
        JsonNode statusJsonNode = node.get("GENRESTRT").get(0).get("head").get(1);
        ClientResponseStatus clientResponseStatus = objectMapper.convertValue(statusJsonNode, ClientResponseStatus.class);
        List<ClientRestaurant> restaurants = objectMapper.convertValue(rowsNode, new TypeReference<>() {
        });
        return ClientResponse.builder()
                .head(Head.builder()
                        .listTotalCount(totalCount)
                        .clientResponseStatus(clientResponseStatus)
                        .apiVersion(apiVersion)
                        .build())
                .clientRestaurants(restaurants)
                .build();
    }
}
