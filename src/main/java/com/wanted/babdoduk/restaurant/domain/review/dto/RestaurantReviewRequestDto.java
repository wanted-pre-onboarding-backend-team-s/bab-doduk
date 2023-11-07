package com.wanted.babdoduk.restaurant.domain.review.dto;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewScore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class RestaurantReviewRequestDto {

    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 0, max = 5, message = "0~5 사이 숫자를 입력해야합니다.")
    private int score;

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 1, message = "1자 이상 입력해주세요.")
    private String comment;

    @Builder
    public RestaurantReviewRequestDto(int score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public RestaurantReview toEntity(Long userId, Long restaurantId) {
        return RestaurantReview.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .score(RestaurantReviewScore.values()[score])
                .comment(comment)
                .build();
    }
}
