package com.wanted.babdoduk.restaurant.domain.review.dto;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RestaurantReviewResponseDto {

    private final Long reviewId;
    private final Long userId;
    private final Long restaurantId;
    private final int score;
    private final String comment;

    @Builder
    public RestaurantReviewResponseDto(Long reviewId, Long userId, Long restaurantId, int score, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.comment = comment;
    }

    public RestaurantReviewResponseDto(RestaurantReview restaurantReview) {
        this.reviewId = restaurantReview.getId();
        this.userId = restaurantReview.getUserId();
        this.restaurantId = restaurantReview.getRestaurantId();
        this.score = restaurantReview.getScore().getValue();
        this.comment = restaurantReview.getComment();
    }
}
