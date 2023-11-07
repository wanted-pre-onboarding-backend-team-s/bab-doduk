package com.wanted.babdoduk.restaurant.domain.review.contoller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.domain.review.service.ReviewService;
import com.wanted.babdoduk.restaurant.domain.review.service.ReviewStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰", description = "리뷰 관련 API")
@RequestMapping("/api/v1/restaurants/{restaurantId}/reviews")
public class RestaurantReviewController {

    private final ReviewService reviewService;
    private final ReviewStatService reviewStatService;

    @Operation(summary = "리뷰 생성", description = "해당 음식점에 리뷰를 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RestaurantReviewResponseDto> createReview(
            @RequestAttribute(required = false) Long userId,

            @Schema(description = "음식점 아이디")
            @PathVariable Long restaurantId,

            @Valid @RequestBody RestaurantReviewRequestDto reviewRequestDto) {

        RestaurantReviewResponseDto createdReview =
                reviewService.createRestaurantReview(1L/*userId*/, restaurantId, reviewRequestDto);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        return ApiResponse.created(createdReview);
    }

    @Operation(summary = "리뷰 수정", description = "해당 음식점의 특정 리뷰를 수정합니다.")
    @PatchMapping("/{reviewId}")
    public ApiResponse updateReview(
            @RequestAttribute(required = false) Long userId,

            @Schema(description = "음식점 아이디")
            @PathVariable Long restaurantId,

            @Schema(description = "리뷰 아이디")
            @PathVariable Long reviewId,

            @RequestBody RestaurantReviewRequestDto reviewRequestDto) {

        reviewService.updateRestaurantReview(1L/*userId*/, reviewId, reviewRequestDto);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        return ApiResponse.noContent();
    }

    @Operation(summary = "리뷰 삭제", description = "해당 음식점의 특정 리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public ApiResponse deleteReview(
            @RequestAttribute(required = false) Long userId,

            @Schema(description = "음식점 아이디")
            @PathVariable Long restaurantId,

            @Schema(description = "리뷰 아이디")
            @PathVariable Long reviewId) {

        reviewService.deleteRestaurantReview(1L/*userId*/, reviewId);
        reviewStatService.updateRestaurantReviewStatIfDeletedReview(restaurantId);
        return ApiResponse.noContent();
    }
}
