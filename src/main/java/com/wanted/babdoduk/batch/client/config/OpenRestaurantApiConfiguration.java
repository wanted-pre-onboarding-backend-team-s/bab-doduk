package com.wanted.babdoduk.batch.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.babdoduk.batch.client.decoder.OpenRestaurantClientDecoder;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenRestaurantApiConfiguration {

    public static final String CONSUME_TYPE = "json";
    private final ObjectMapper objectMapper;

    @Value("${feign.openapi.key}")
    private String API_KEY;

    public OpenRestaurantApiConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .query("Type", CONSUME_TYPE)
                .query("KEY", API_KEY);
    }

    @Bean
    public Decoder decoder() {
        return new OpenRestaurantClientDecoder(objectMapper);
    }


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
