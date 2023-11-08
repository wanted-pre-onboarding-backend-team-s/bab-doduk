package com.wanted.babdoduk.session.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.session.dto.LoginRequestDto;
import com.wanted.babdoduk.session.dto.LoginResultDto;
import com.wanted.babdoduk.session.exception.PasswordMismatchedException;
import com.wanted.babdoduk.session.exception.TokenIssuanceFailedException;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResultDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        verifyPasswordMatches(password, user);

        UserRefreshToken userRefreshToken = userRefreshTokenRepository
                .findByUserId(user.id());

        try {
            if (userRefreshToken.isNotIssued() || userRefreshToken.isExpired(jwtUtil)) {
                userRefreshToken.issue(jwtUtil);
            }

            String accessToken = jwtUtil.issueAccessToken(user.id());

            return LoginResultDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(userRefreshToken.value())
                    .build();

        } catch (JWTCreationException exception) {
            throw new TokenIssuanceFailedException();
        }
    }

    private void verifyPasswordMatches(String password, User user) {
        if (!user.passwordMatches(passwordEncoder, password)) {
            throw new PasswordMismatchedException();
        }
    }
}
