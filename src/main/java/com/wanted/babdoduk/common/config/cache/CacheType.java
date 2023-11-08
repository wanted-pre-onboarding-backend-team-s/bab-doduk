package com.wanted.babdoduk.common.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    Restaurant_Detail("restaurant", 6000, 40000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }
}
