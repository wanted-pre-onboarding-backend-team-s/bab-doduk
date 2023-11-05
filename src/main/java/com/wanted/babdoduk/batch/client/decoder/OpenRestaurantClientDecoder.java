package com.wanted.babdoduk.batch.client.decoder;

import static java.lang.String.format;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class OpenRestaurantClientDecoder implements Decoder {

    private static final String API_TITLE = "GENRESTRT";
    private final ObjectMapper objectMapper;

    public OpenRestaurantClientDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        JsonNode responseJsonNode = objectMapper.readTree(bodyStr);
        if (hasErrorCode(responseJsonNode)) {
            // TODO throw Custom Exception Wrapped Our Custom Exception with Api Result Code
        }
        JsonNode rowNode = responseJsonNode.get(API_TITLE).get(1).get("row");
        return objectMapper.convertValue(rowNode, new TypeReference<>() {});
    }

    private boolean hasErrorCode(JsonNode jsonNode) {
        return !jsonNode.has(API_TITLE);
    }
}
