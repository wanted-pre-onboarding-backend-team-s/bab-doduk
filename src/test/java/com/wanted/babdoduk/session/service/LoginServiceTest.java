package com.wanted.babdoduk.session.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.auth0.jwt.algorithms.Algorithm;
import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.session.dto.LoginRequestDto;
import com.wanted.babdoduk.session.dto.LoginResultDto;
import com.wanted.babdoduk.session.exception.PasswordMismatchedException;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRefreshTokenRepository userRefreshTokenRepository;

    @Spy
    private PasswordEncoder passwordEncoder = Argon2PasswordEncoder
            .defaultsForSpringSecurity_v5_8();

    private static final String JWT_SECRET = "SECRET";
    private static final Long VALID_TIME_ACCESS_TOKEN = 1000L;
    private static final Long VALID_TIME_REFRESH_TOKEN = 3000L;

    @Spy
    private JwtUtil jwtUtil = new JwtUtil(
            Algorithm.HMAC256(JWT_SECRET),
            VALID_TIME_ACCESS_TOKEN,
            VALID_TIME_REFRESH_TOKEN
    );

    private static final Long USER_ID = 252523L;
    private static final String USERNAME = "hsjkdss228";
    private static final String PASSWORD = "Password!1";

    private final User user = User.builder()
            .id(USER_ID)
            .build();

    private final LoginRequestDto loginRequestDto = LoginRequestDto.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .build();

    @DisplayName("성공: Access Token, Refresh Token 반환")
    @Nested
    class Success {

        @BeforeEach
        void setUp() {
            user.changePassword(passwordEncoder, PASSWORD);

            given(userRepository.findByUsername(USERNAME))
                    .willReturn(Optional.of(user));
        }

        @DisplayName("발행된 Refresh Token 존재하고 만료되지 않은 경우, "
                + "Access Token만 발행")
        @Test
        void issueOnlyAccessToken() {
            String refreshToken = jwtUtil.issueRefreshToken(USER_ID);
            UserRefreshToken userRefreshToken = spy(UserRefreshToken.builder()
                    .user(user)
                    .value(refreshToken)
                    .build());
            given(userRefreshTokenRepository.findByUserId(USER_ID))
                    .willReturn(userRefreshToken);

            LoginResultDto loginResultDto = loginService.login(loginRequestDto);

            assertThat(loginResultDto).isNotNull();
            assertThat(loginResultDto.accessToken()).isNotNull();
            assertThat(loginResultDto.refreshToken()).isEqualTo(refreshToken);

            verify(userRefreshToken, never()).reissue(jwtUtil);
            verify(jwtUtil).issueAccessToken(USER_ID);
        }

        @DisplayName("발행된 Refresh Token 존재하지 않는 경우, "
                + "Refresh Token 재발행 및 Access Token 발행")
        @Test
        void issueRefreshAndAccessTokenWhenRefreshTokenNotExists() {
            UserRefreshToken userRefreshToken = spy(UserRefreshToken.builder()
                    .user(user)
                    .build());
            given(userRefreshTokenRepository.findByUserId(USER_ID))
                    .willReturn(userRefreshToken);

            LoginResultDto loginResultDto = loginService.login(loginRequestDto);

            assertThat(loginResultDto).isNotNull();
            assertThat(loginResultDto.accessToken()).isNotNull();
            assertThat(loginResultDto.refreshToken()).isNotNull();

            verify(userRefreshToken).reissue(jwtUtil);
            verify(jwtUtil).issueAccessToken(USER_ID);
        }

        @DisplayName("발행된 Refresh Token 만료된 경우, "
                + "Refresh Token 재발행 및 Access Token 발행")
        @Test
        void issueRefreshAndAccessTokenWhenRefreshTokenExpires() throws InterruptedException {
            String refreshToken = jwtUtil.issueRefreshToken(USER_ID);
            UserRefreshToken userRefreshToken = spy(UserRefreshToken.builder()
                    .user(user)
                    .value(refreshToken)
                    .build());
            given(userRefreshTokenRepository.findByUserId(USER_ID))
                    .willReturn(userRefreshToken);

            Thread.sleep(VALID_TIME_REFRESH_TOKEN);

            LoginResultDto loginResultDto = loginService.login(loginRequestDto);

            assertThat(loginResultDto).isNotNull();
            assertThat(loginResultDto.accessToken()).isNotNull();
            assertThat(loginResultDto.refreshToken()).isNotNull();
            assertThat(loginResultDto.refreshToken()).isNotEqualTo(refreshToken);

            verify(userRefreshToken).reissue(jwtUtil);
            verify(jwtUtil).issueAccessToken(USER_ID);
        }
    }

    @DisplayName("실패")
    @Nested
    class Fail {

        private static final String MISMATCHED_PASSWORD = "1234567898765432";

        @DisplayName("계정명과 일치하는 계정이 존재하지 않을 경우 예외처리")
        @Test
        void userNotFound() {
            given(userRepository.findByUsername(USERNAME))
                    .willThrow(UserNotFoundException.class);

            assertThrows(UserNotFoundException.class, () -> loginService
                    .login(loginRequestDto));

            verify(userRefreshTokenRepository, never()).findByUserId(any(Long.class));
        }

        @DisplayName("비밀번호가 일치하지 않을 경우 예외처리")
        @Test
        void passwordMismatched() {
            user.changePassword(passwordEncoder, MISMATCHED_PASSWORD);

            given(userRepository.findByUsername(USERNAME))
                    .willReturn(Optional.of(user));

            assertThrows(PasswordMismatchedException.class, () -> loginService
                    .login(loginRequestDto));

            verify(userRefreshTokenRepository, never()).findByUserId(any(Long.class));
        }
    }
}
