package com.wanted.babdoduk.session.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceRequestDto;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceResponseDto;
import com.wanted.babdoduk.session.exception.RefreshTokenDifferentException;
import com.wanted.babdoduk.session.exception.RefreshTokenNotIssuedException;
import com.wanted.babdoduk.session.exception.TokenDecodingFailedException;
import com.wanted.babdoduk.session.exception.RefreshTokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueAccessTokenService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional(noRollbackFor = RefreshTokenDifferentException.class)
    public AccessTokenReissuanceResponseDto reissueAccessToken(
            AccessTokenReissuanceRequestDto accessTokenReissuanceRequestDto
    ) {
        String refreshToken = accessTokenReissuanceRequestDto.getRefreshToken();

        try {
            Long userId = jwtUtil.decodeRefreshToken(refreshToken);

            UserRefreshToken userRefreshToken = userRefreshTokenRepository
                    .findByUserId(userId);

            if (userRefreshToken.isNotIssued()) {
                throw new RefreshTokenNotIssuedException();
            }

            if (userRefreshToken.isNotSameAs(refreshToken)) {
                userRefreshToken.expire();
                throw new RefreshTokenDifferentException();
            }

            String accessToken = jwtUtil.issueAccessToken(userId);

            return AccessTokenReissuanceResponseDto.builder()
                    .accessToken(accessToken)
                    .build();

        } catch (JWTDecodeException exception) {
            throw new TokenDecodingFailedException();

        } catch (TokenExpiredException exception) {
            throw new RefreshTokenExpiredException();
        }
    }
}
