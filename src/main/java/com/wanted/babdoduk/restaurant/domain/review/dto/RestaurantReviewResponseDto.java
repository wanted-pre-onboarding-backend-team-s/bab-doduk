package com.wanted.babdoduk.restaurant.domain.review.dto;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RestaurantReviewResponseDto {

    @Schema(title = "created review id", description = "생성된 리뷰 아이디")
    private final Long reviewId;

    @Schema(title = "user id", description = "사용자 아이디")
    private final Long userId;

    @Schema(title = "restaurant id", description = "음식점 아이디")
    private final Long restaurantId;

    @Schema(title = "review score", description = "리뷰 점수")
    private final int score;

    @Schema(title = "review comment", description = "리뷰 내용")
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
