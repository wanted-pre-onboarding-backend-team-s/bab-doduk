package com.wanted.babdoduk.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class AccessTokenReissuanceRequestDto {
    @Schema(title = "Refresh Token", description = "리프레시 토큰")
    @NotBlank
    private String refreshToken;
}
