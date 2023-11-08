package com.wanted.babdoduk.restaurant.dto;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantReviewResponseDto {

    private int score;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantReviewResponseDto of(RestaurantReview review) {
        return new RestaurantReviewResponseDto(
                review.getScore().getValue(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
