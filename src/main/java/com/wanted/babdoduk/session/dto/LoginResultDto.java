package com.wanted.babdoduk.session.dto;

import lombok.Builder;

@Builder
public record LoginResultDto(
        String accessToken,
        String refreshToken
) {

}
