package com.wanted.babdoduk.common.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 적용된 메소드의 리턴값을 기준으로 캐시에 저장하는데 service 각각 메서드에 @Cacheable 을 붙이는 게 나을까 controller 메서드 하나에 붙이는 게 나을까
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                                           .map(cache -> new CaffeineCache(
                                                   cache.getCacheName(),
                                                   Caffeine.newBuilder()
                                                           .recordStats()
                                                           .expireAfterWrite(
                                                                   cache.getExpiredAfterWrite(),
                                                                   TimeUnit.SECONDS)
                                                           .maximumSize(cache.getMaximumSize())
                                                           .build()))
                                           .collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
