package com.wanted.babdoduk.user.domain.entity;

import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import com.wanted.babdoduk.user.dto.CreateUserResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "lat")
    private BigDecimal latitude;

    @Column(name = "lon")
    private BigDecimal longitude;

    @Column(name = "lunch_push_approved")
    private Boolean lunchPushApproved;

    @Builder
    public User(Long id,
            String username,
            String encodedPassword,
            BigDecimal latitude,
            BigDecimal longitude,
            Boolean lunchPushApproved) {
        this.id = id;
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lunchPushApproved = lunchPushApproved;
    }

    public Long id() {
        return id;
    }

    public void changePassword(PasswordEncoder passwordEncoder, String password) {
        this.encodedPassword = passwordEncoder.encode(password);
    }

    public boolean passwordMatches(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.encodedPassword);
    }

    public CreateUserResponseDto toCreateUserResponseDto() {
        return CreateUserResponseDto.builder()
                .userId(id)
                .build();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

}
