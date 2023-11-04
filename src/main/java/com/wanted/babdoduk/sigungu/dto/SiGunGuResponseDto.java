package com.wanted.babdoduk.sigungu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SiGunGuResponseDto {

    private final String doSi;
    private final String siGunGu;
    private final double lat;
    private final double lon;

    @Builder
    public SiGunGuResponseDto(String doSi, String siGunGu, double lat, double lon) {
        this.doSi = doSi;
        this.siGunGu = siGunGu;
        this.lat = lat;
        this.lon = lon;
    }
}
