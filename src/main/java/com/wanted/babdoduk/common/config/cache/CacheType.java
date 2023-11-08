package com.wanted.babdoduk.common.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    Restaurant_Detail("restaurant", 6000);

    private final String cacheName;
    private final int expireTime;

    CacheType(String cacheName, int expireTime) {
        this.cacheName = cacheName;
        this.expireTime = expireTime;
    }

}
