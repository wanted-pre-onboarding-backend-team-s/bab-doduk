package com.wanted.babdoduk.restaurant.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RestaurantReviewResponseDto {

    private Long reviewId;
    private Long userId;
    private Long restaurantId;
    private int score;
    private String comment;

    @Builder
    public RestaurantReviewResponseDto(Long reviewId, Long userId, Long restaurantId, int score, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.comment = comment;
    }
}
