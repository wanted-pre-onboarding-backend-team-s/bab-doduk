package com.wanted.babdoduk.batch.client.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wanted.babdoduk.batch.client.response.common.ClientResponseStatus;
import java.io.IOException;

public class CustomClientResponseStatusDeserializer extends JsonDeserializer<ClientResponseStatus> {

    @Override
    public ClientResponseStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode node = objectCodec.readTree(p);
        String code = node.get("RESULT").get("CODE").asText();
        String message = node.get("RESULT").get("MESSAGE").asText();
        return ClientResponseStatus.builder()
                .code(code)
                .message(message)
                .build();
    }
}
