package com.wanted.babdoduk.batch.client.response.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanted.babdoduk.batch.client.serializer.CustomClientResponseStatusDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonDeserialize(using = CustomClientResponseStatusDeserializer.class)
public class ClientResponseStatus {

    @JsonProperty("CODE")
    private String code;

    @JsonProperty("MESSAGE")
    private String message;

    @Builder
    public ClientResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
