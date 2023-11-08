package com.wanted.babdoduk.restaurant.dto;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantDetailResponseDto {

    private String manageNo;
    private String sigunName;
    private String sigunCode;
    private String bizName;
    private String bizStatus;
    private String cuisineType;
    private String roadAddr;
    private String jibunAddr;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double rating;
    private List<RestaurantReviewResponseDto> reviews;

    public static RestaurantDetailResponseDto of(
            Restaurant restaurant,
            double rating,
            List<RestaurantReviewResponseDto> reviews
    ) {
        return RestaurantDetailResponseDto.builder()
                                          .manageNo(restaurant.getManageNo())
                                          .sigunName(restaurant.getSigunName())
                                          .sigunCode(restaurant.getSigunCode())
                                          .bizName(restaurant.getBizName())
                                          .bizStatus(restaurant.getBizStatus())
                                          .cuisineType(restaurant.getCuisineType())
                                          .roadAddr(restaurant.getRoadAddr())
                                          .jibunAddr(restaurant.getJibunAddr())
                                          .latitude(restaurant.getLatitude())
                                          .longitude(restaurant.getLongitude())
                                          .createdAt(restaurant.getCreatedAt())
                                          .updatedAt(restaurant.getUpdatedAt())
                                          .rating(rating)
                                          .reviews(reviews)
                                          .build();
    }
}
