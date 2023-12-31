package com.wanted.babdoduk.user.service;

import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import com.wanted.babdoduk.session.domain.repository.UserRefreshTokenRepository;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.dto.CreateUserRequestDto;
import com.wanted.babdoduk.user.dto.CreateUserResponseDto;
import com.wanted.babdoduk.user.exception.UsernameDuplicatedException;
import com.wanted.babdoduk.user.exception.ReconfirmPasswordMismatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponseDto createUser(
            CreateUserRequestDto createUserRequestDto
    ) {
        String username = createUserRequestDto.getUsername();
        String password = createUserRequestDto.getPassword();
        String reconfirmPassword = createUserRequestDto.getReconfirmPassword();

        verifyUsernameDuplication(username);
        verifyReconfirmPassword(password, reconfirmPassword);

        User user = createUser(username, password);
        User savedUser = userRepository.save(user);

        UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                .user(savedUser)
                .build();
        userRefreshTokenRepository.save(userRefreshToken);

        return savedUser.toCreateUserResponseDto();
    }

    private void verifyUsernameDuplication(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameDuplicatedException();
        }
    }

    private void verifyReconfirmPassword(String password, String reconfirmPassword) {
        if (!password.equals(reconfirmPassword)) {
            throw new ReconfirmPasswordMismatchedException();
        }
    }

    private User createUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .lunchPushApproved(false)
                .build();

        user.changePassword(passwordEncoder, password);

        return user;
    }
}
