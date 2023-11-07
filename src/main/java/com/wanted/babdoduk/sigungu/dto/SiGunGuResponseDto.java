package com.wanted.babdoduk.sigungu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SiGunGuResponseDto {

    @Schema(title = "do-si", description = "도,시")
    private final String doSi;

    @Schema(title = "si-gun-gu", description = "시,군,구")
    private final String siGunGu;

    @Schema(title = "latitude", description = "시도, 시군구의 위도")
    private final double lat;

    @Schema(title = "longitude", description = "시도, 시군구의 경도")
    private final double lon;

    @Builder
    public SiGunGuResponseDto(String doSi, String siGunGu, double lat, double lon) {
        this.doSi = doSi;
        this.siGunGu = siGunGu;
        this.lat = lat;
        this.lon = lon;
    }
}
