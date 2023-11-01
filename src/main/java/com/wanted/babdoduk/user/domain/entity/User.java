package com.wanted.babdoduk.user.domain.entity;

import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
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
    public User(String username,
            String encodedPassword,
            BigDecimal latitude,
            BigDecimal longitude,
            Boolean lunchPushApproved) {
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lunchPushApproved = lunchPushApproved;
    }
}
