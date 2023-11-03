package com.wanted.babdoduk.restaurant.domain.restaurant;

import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import jakarta.persistence.Column;
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

    @Column(name = "lat")
    private BigDecimal latitude;

    @Column(name = "lon")
    private BigDecimal longitude;

    @Builder
    public Restaurant(
            String sigunName, String bizName, String bizStatus, String cuisineType,
            String roadAddr, String jibunAddr, BigDecimal latitude, BigDecimal longitude
    ) {
        this.sigunName = sigunName;
        this.bizName = bizName;
        this.bizStatus = bizStatus;
        this.cuisineType = cuisineType;
        this.roadAddr = roadAddr;
        this.jibunAddr = jibunAddr;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void verifyClosed() {
        if (this.bizStatus.equals(BusinessStatus.Close.getStatus())) {
            throw new ClosedRestaurantException();
        }
    }
}
