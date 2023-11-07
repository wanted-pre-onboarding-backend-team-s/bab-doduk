package com.wanted.babdoduk.restaurant.domain.review.contoller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.domain.review.service.ReviewService;
import com.wanted.babdoduk.restaurant.domain.review.service.ReviewStatService;
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
@RequestMapping("/api/v1/restaurants/{restaurantId}/reviews")
public class RestaurantReviewController {

    private final ReviewService reviewService;
    private final ReviewStatService reviewStatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RestaurantReviewResponseDto> createReview(
            @RequestAttribute(required = false) Long userId,
            @PathVariable Long restaurantId,
            @Valid @RequestBody RestaurantReviewRequestDto reviewRequestDto) {

        RestaurantReviewResponseDto createdReview =
                reviewService.createRestaurantReview(1L/*userId*/, restaurantId, reviewRequestDto);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        return ApiResponse.created(createdReview);
    }

    @PatchMapping("/{reviewId}")
    public ApiResponse updateReview(
            @RequestAttribute(required = false) Long userId,
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId,
            @RequestBody RestaurantReviewRequestDto reviewRequestDto) {

        reviewService.updateRestaurantReview(1L/*userId*/, reviewId, reviewRequestDto);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        return ApiResponse.noContent();
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse deleteReview(
            @RequestAttribute(required = false) Long userId,
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId) {

        reviewService.deleteRestaurantReview(1L/*userId*/, reviewId);
        reviewStatService.updateRestaurantReviewStatIfDeletedReview(restaurantId);
        return ApiResponse.noContent();
    }
}
