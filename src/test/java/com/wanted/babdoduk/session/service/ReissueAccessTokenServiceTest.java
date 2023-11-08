package com.wanted.babdoduk.session.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.auth0.jwt.algorithms.Algorithm;
import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceRequestDto;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceResponseDto;
import com.wanted.babdoduk.session.exception.RefreshTokenExpiredException;
import com.wanted.babdoduk.session.exception.RefreshTokenNotIssuedException;
import com.wanted.babdoduk.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReissueAccessTokenServiceTest {

    @InjectMocks
    private ReissueAccessTokenService reissueAccessTokenService;

    @Mock
    private UserRefreshTokenRepository userRefreshTokenRepository;

    private static final String JWT_SECRET = "SECRET";
    private static final Long VALID_TIME_ACCESS_TOKEN = 1000L;
    private static final Long VALID_TIME_REFRESH_TOKEN = 3000L;

    @Spy
    private JwtUtil jwtUtil = new JwtUtil(
            Algorithm.HMAC256(JWT_SECRET),
            VALID_TIME_ACCESS_TOKEN,
            VALID_TIME_REFRESH_TOKEN
    );

    private static final Long USER_ID = 2277L;

    private final User user = User.builder()
            .id(USER_ID)
            .build();

    @DisplayName("성공: 예외처리 없이 액세스 토큰 재발행")
    @Test
    void success() {
        String refreshToken = jwtUtil.issueRefreshToken(USER_ID);
        UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                .user(user)
                .value(refreshToken)
                .build();

        given(userRefreshTokenRepository.findByUserId(USER_ID))
                .willReturn(userRefreshToken);

        AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
                = AccessTokenReissuanceRequestDto.builder()
                .refreshToken(refreshToken)
                .build();

        AccessTokenReissuanceResponseDto accessTokenReissuanceResponseDto
                = reissueAccessTokenService.reissueAccessToken(accessTokenReissuanceRequestDto);

        assertThat(accessTokenReissuanceResponseDto.accessToken()).isNotNull();
    }

    @DisplayName("실패: 발행된 리프레시 토큰이 없었던 경우 예외 발생")
    @Test
    void refreshTokenNotIssued() {
        UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                .user(user)
                .value(null)
                .build();

        given(userRefreshTokenRepository.findByUserId(USER_ID))
                .willReturn(userRefreshToken);

        String refreshToken = jwtUtil.issueRefreshToken(USER_ID);
        AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
                = AccessTokenReissuanceRequestDto.builder()
                .refreshToken(refreshToken)
                .build();

        assertThrows(RefreshTokenNotIssuedException.class, () -> reissueAccessTokenService
                .reissueAccessToken(accessTokenReissuanceRequestDto));

        verify(jwtUtil, never()).issueAccessToken(USER_ID);
    }

    @DisplayName("실패: 발행된 리프레시 토큰이 만료된 경우 예외 발생")
    @Test
    void refreshTokenDifferent() {
        String refreshToken = jwtUtil.issueRefreshToken(USER_ID);
        AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
                = AccessTokenReissuanceRequestDto.builder()
                .refreshToken(refreshToken)
                .build();

        assertThrows(RefreshTokenExpiredException.class, () -> {
            Thread.sleep(VALID_TIME_REFRESH_TOKEN);
            reissueAccessTokenService.reissueAccessToken(accessTokenReissuanceRequestDto);
        });

        verify(userRefreshTokenRepository, never()).findByUserId(any(Long.class));
        verify(jwtUtil, never()).issueAccessToken(USER_ID);
    }
}
