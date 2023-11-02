package com.wanted.babdoduk.restaurant.dto;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantDetailResponseDto {

    private String sigunName;
    private String bizName;
    private String bizStatus;
    private String cuisineType;
    private String roadAddr;
    private String jibunAddr;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double stat;
    private List<RestaurantReviewResponseDto> reviews;

    public static RestaurantDetailResponseDto of(
            Restaurant restaurant,
            double stat,
            List<RestaurantReviewResponseDto> reviews
    ) {
        return new RestaurantDetailResponseDto(
                restaurant.getSigunName(),
                restaurant.getBizName(),
                restaurant.getBizStatus(),
                restaurant.getCuisineType(),
                restaurant.getRoadAddr(),
                restaurant.getJibunAddr(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt(),
                stat,
                reviews
        );
    }
}
