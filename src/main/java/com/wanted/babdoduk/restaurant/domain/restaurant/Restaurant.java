package com.wanted.babdoduk.restaurant.domain.restaurant;

import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "restaurants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sigunName;
    private String bizName;
    private String bizStatus;
    private String cuisineType;
    private String roadAddr;
    private String jibunAddr;
    private BigDecimal lat;
    private BigDecimal lon;

    @Builder
    public Restaurant(
            String sigunName, String bizName, String bizStatus, String cuisineType,
            String roadAddr, String jibunAddr, BigDecimal lat, BigDecimal lon
    ) {
        this.sigunName = sigunName;
        this.bizName = bizName;
        this.bizStatus = bizStatus;
        this.cuisineType = cuisineType;
        this.roadAddr = roadAddr;
        this.jibunAddr = jibunAddr;
        this.lat = lat;
        this.lon = lon;
    }
}
