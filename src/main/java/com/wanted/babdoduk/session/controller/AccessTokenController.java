package com.wanted.babdoduk.session.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceRequestDto;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceResponseDto;
import com.wanted.babdoduk.session.service.ReissueAccessTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "세션")
@RestController
@RequestMapping("/api/v1/access-tokens")
@RequiredArgsConstructor
public class AccessTokenController {

    private final ReissueAccessTokenService reissueAccessTokenService;

    @Operation(summary = "액세스 토큰 재발행")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AccessTokenReissuanceResponseDto> reissue(
            @Valid @RequestBody AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
    ) {
        return ApiResponse.created(reissueAccessTokenService
                .reissueAccessToken(accessTokenReissuanceRequestDto));
    }
}
