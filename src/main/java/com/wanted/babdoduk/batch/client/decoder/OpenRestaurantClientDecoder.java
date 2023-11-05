package com.wanted.babdoduk.batch.client.decoder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.babdoduk.batch.client.OpenRestaurantClient;
import com.wanted.babdoduk.batch.exception.RestaurantClientException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenRestaurantClientDecoder implements Decoder {

    private static final String API_TITLE = "GENRESTRT";
    private final ObjectMapper objectMapper;

    public OpenRestaurantClientDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        JsonNode responseJsonNode = objectMapper.readTree(bodyStr);
        if (hasErrorCode(responseJsonNode)) {
            JsonNode codeNode = responseJsonNode.get("RESULT").get("CODE");

            // Open API 의 응답 상태 200 이면서 에러코드 반환 케이스
            throw new RestaurantClientException(codeNode.asText(), response.request());
        }
        JsonNode rowNode = responseJsonNode.get(API_TITLE).get(1).get("row");
        return objectMapper.convertValue(rowNode, new TypeReference<>() {});
    }

    private boolean hasErrorCode(JsonNode jsonNode) {
        return !jsonNode.has(API_TITLE);
    }
}
