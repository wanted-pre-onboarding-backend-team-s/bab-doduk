package com.wanted.babdoduk.session.domain.entity;

import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.user.domain.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRefreshToken {

    @Id
    private Long userId;

    @Column(name = "value")
    private String value;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public UserRefreshToken(User user, String value) {
        this.userId = user.id();
        this.value = value;
    }

    public String value() {
        return value;
    }

    public boolean isNotIssued() {
        return value == null;
    }

    public boolean isNotSameAs(String refreshToken) {
        return !value.equals(refreshToken);
    }

    public boolean isExpired(JwtUtil jwtUtil) {
        if (value == null) {
            return true;
        }
        return jwtUtil.isTokenExpired(value);
    }

    public void reissue(JwtUtil jwtUtil) {
        value = jwtUtil.issueRefreshToken(userId);
    }

    public void expire() {
        value = null;
    }
}
