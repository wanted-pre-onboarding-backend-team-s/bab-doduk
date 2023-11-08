package com.wanted.babdoduk.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.dto.CreateUserRequestDto;
import com.wanted.babdoduk.user.dto.CreateUserResponseDto;
import com.wanted.babdoduk.user.exception.UsernameDuplicatedException;
import com.wanted.babdoduk.user.exception.ReconfirmPasswordMismatchedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @InjectMocks
    private CreateUserService createUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRefreshTokenRepository userRefreshTokenRepository;

    @Mock
    private Argon2PasswordEncoder argon2PasswordEncoder;

    private static final Long USER_ID = 122212L;
    private static final String USERNAME = "hsjkdss228";
    private static final String PASSWORD = "Password!1";
    private static final String RECONFIRM_PASSWORD = "Password!1";

    @DisplayName("성공: 예외처리 없이 User Entity 생성 후 CreateUserResponseDto 반환")
    @Test
    void createUser() {
        given(userRepository.existsByUsername(USERNAME))
                .willReturn(false);

        User savedUser = User.builder()
                .id(USER_ID)
                .build();
        given(userRepository.save(any(User.class)))
                .willReturn(savedUser);

        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .reconfirmPassword(RECONFIRM_PASSWORD)
                .build();
        CreateUserResponseDto createUserResponseDto = createUserService
                .createUser(createUserRequestDto);

        assertThat(createUserResponseDto).isNotNull();
        assertThat(createUserResponseDto.userId()).isEqualTo(USER_ID);

        verify(argon2PasswordEncoder).encode(PASSWORD);
        verify(userRefreshTokenRepository).save(any(UserRefreshToken.class));
    }

    private static final String EXISTING_USERNAME = "asdfasdfa324";

    @DisplayName("실패: 계정명 중복 시 예외처리")
    @Test
    void duplicatedUsername() {
        given(userRepository.existsByUsername(EXISTING_USERNAME))
                .willReturn(true);

        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .username(EXISTING_USERNAME)
                .password(PASSWORD)
                .reconfirmPassword(RECONFIRM_PASSWORD)
                .build();
        assertThrows(UsernameDuplicatedException.class, () -> createUserService
                .createUser(createUserRequestDto));

        verify(userRepository, never()).save(any(User.class));
        verify(argon2PasswordEncoder, never()).encode(PASSWORD);
    }

    private static final String MISMATCHED_RECONFIRM_PASSWORD = "Passworld@2";

    @DisplayName("실패: 비밀번호 재확인 불일치 시 예외처리")
    @Test
    void mismatchedReconfirmPassword() {
        given(userRepository.existsByUsername(USERNAME))
                .willReturn(false);

        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .reconfirmPassword(MISMATCHED_RECONFIRM_PASSWORD)
                .build();
        assertThrows(ReconfirmPasswordMismatchedException.class, () -> createUserService
                .createUser(createUserRequestDto));

        verify(userRepository, never()).save(any(User.class));
        verify(argon2PasswordEncoder, never()).encode(PASSWORD);
    }
}
