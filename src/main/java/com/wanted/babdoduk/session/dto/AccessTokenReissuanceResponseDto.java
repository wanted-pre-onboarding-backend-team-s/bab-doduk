package com.wanted.babdoduk.session.dto;

import lombok.Builder;

@Builder
public record AccessTokenReissuanceResponseDto(
        String accessToken
) {

}
