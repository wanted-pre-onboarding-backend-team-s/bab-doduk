package com.wanted.babdoduk.common.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.wanted.babdoduk.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.valid-time.access-token}")
    private Long validTimeAccessToken;

    @Value("${jwt.valid-time.refresh-token}")
    private Long validTimeRefreshToken;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(
                Algorithm.HMAC256(jwtSecret),
                validTimeAccessToken,
                validTimeRefreshToken);
    }
}
