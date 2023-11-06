package com.wanted.babdoduk.restaurant.domain.review.dto;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantReviewStatDto {

    private Long restaurantId;
    private int reviewCount = 0;
    private Double averageScore = 0.0;

    public RestaurantReviewStatDto(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Builder
    public RestaurantReviewStatDto(Long restaurantId, int reviewCount, Double averageScore) {
        this.restaurantId = restaurantId;
        this.reviewCount = reviewCount;
        this.averageScore = averageScore;
    }

    public RestaurantReviewStat toEntity() {
        return RestaurantReviewStat.builder()
                .restaurantId(restaurantId)
                .reviewCount(reviewCount)
                .averageScore(averageScore)
                .build();
    }
}
