package com.wanted.babdoduk.session.domain.repository;

import com.wanted.babdoduk.session.domain.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository
        extends JpaRepository<UserRefreshToken, Long> {

    UserRefreshToken findByUserId(Long userId);
}
