package com.wanted.babdoduk.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtUtil {

    private static final String CLAIM_USER_ID = "userId";

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

    public Long decodeRefreshToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT verified = verifier.verify(token);
        return verified.getClaim(CLAIM_USER_ID)
                .asLong();
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
