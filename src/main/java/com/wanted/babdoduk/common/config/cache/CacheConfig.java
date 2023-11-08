package com.wanted.babdoduk.common.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration configuration =
                RedisCacheConfiguration.defaultCacheConfig()
                                       .disableCachingNullValues()
                                       .serializeKeysWith(
                                               RedisSerializationContext.SerializationPair.fromSerializer(
                                                       new StringRedisSerializer()))
                                       .serializeValuesWith(
                                               RedisSerializationContext.SerializationPair.fromSerializer(
                                                       new GenericJackson2JsonRedisSerializer(objectMapper())));

        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put(CacheType.Restaurant_Detail.getCacheName(),
                           configuration.entryTtl(Duration.ofSeconds(CacheType.Restaurant_Detail.getExpireTime())));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(configurations).build();
    }

    private ObjectMapper objectMapper() {
        PolymorphicTypeValidator validator =
                BasicPolymorphicTypeValidator.builder().
                                             allowIfSubType(Object.class)
                                             .build();

        return new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                 .registerModule(new JavaTimeModule())
                                 .activateDefaultTyping(validator, DefaultTyping.NON_FINAL);
    }

}
