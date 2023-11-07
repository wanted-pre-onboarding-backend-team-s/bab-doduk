package com.wanted.babdoduk.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtUtil {

    private final Algorithm algorithm;
    private final Long validTimeAccessToken;
    private final Long validTimeRefreshToken;

    public String issueAccessToken(Long userId) {
        Date now = new Date();
        Date expirationDateAccessToken = new Date(now.getTime() + validTimeAccessToken);

        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(expirationDateAccessToken)
                .sign(algorithm);
    }

    public String issueRefreshToken(Long userId) {
        Date now = new Date();
        Date expirationDateRefreshToken = new Date(now.getTime() + validTimeRefreshToken);

        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(expirationDateRefreshToken)
                .sign(algorithm);
    }

    public boolean isTokenExpired(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return false;
        } catch (TokenExpiredException exception) {
            return true;
        }
    }
}
