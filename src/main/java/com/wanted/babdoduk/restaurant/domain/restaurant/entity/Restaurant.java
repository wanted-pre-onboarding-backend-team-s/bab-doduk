package com.wanted.babdoduk.restaurant.domain.restaurant.entity;

import com.wanted.babdoduk.batch.RawRestaurant;
import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import com.wanted.babdoduk.restaurant.domain.restaurant.enums.BusinessStatus;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
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

    private String manageNo;
    private String sigunName;
    private String sigunCode;
    private String bizName;
    private String bizStatus;
    private String cuisineType;
    private String roadAddr;
    private String jibunAddr;

    @Column(name = "lat")
    protected BigDecimal latitude;

    @Column(name = "lon")
    protected BigDecimal longitude;

    @Builder
    public Restaurant(
            String manageNo, String sigunName, String sigunCode, String bizName, String bizStatus,
            String cuisineType, String roadAddr, String jibunAddr, BigDecimal latitude, BigDecimal longitude) {
        this.manageNo = manageNo;
        this.sigunName = sigunName;
        this.sigunCode = sigunCode;
        this.bizName = bizName;
        this.bizStatus = bizStatus;
        this.cuisineType = cuisineType;
        this.roadAddr = roadAddr;
        this.jibunAddr = jibunAddr;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(RawRestaurant restaurant) {
        this.manageNo = restaurant.getManageNo();
        this.bizName = restaurant.getName();
        this.cuisineType = restaurant.getCuisineType();
        this.bizStatus = restaurant.getUnityBsnStateNm();
        this.jibunAddr = restaurant.getJibunAddr();
        this.roadAddr = restaurant.getRoadAddr();
        this.latitude = new BigDecimal(restaurant.getLat());
        this.longitude = new BigDecimal(restaurant.getLon());
        this.sigunCode = restaurant.getSigunCd();
        this.sigunName = restaurant.getSigunNm();
    }

    public static Restaurant createFromRaw(RawRestaurant rawRestaurant) {
        return Restaurant.builder()
                .longitude(new BigDecimal(rawRestaurant.getLon()))
                .latitude(new BigDecimal(rawRestaurant.getLat()))
                .manageNo(rawRestaurant.getManageNo())
                .jibunAddr(rawRestaurant.getJibunAddr())
                .roadAddr(rawRestaurant.getRoadAddr())
                .cuisineType(rawRestaurant.getCuisineType())
                .sigunName(rawRestaurant.getSigunNm())
                .sigunCode(rawRestaurant.getSigunCd())
                .bizName(rawRestaurant.getName())
                .bizStatus(rawRestaurant.getBsnStateNm())
                .build();
    }

    public void verifyClosed() {
        if (this.bizStatus.equals(BusinessStatus.Close.getStatus())) {
            throw new ClosedRestaurantException();
        }
    }
}
