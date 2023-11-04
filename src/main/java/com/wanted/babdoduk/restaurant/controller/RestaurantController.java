package com.wanted.babdoduk.restaurant.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.restaurant.dto.RestaurantDetailResponseDto;
import com.wanted.babdoduk.restaurant.service.RestaurantReviewService;
import com.wanted.babdoduk.restaurant.service.RestaurantReviewStatService;
import com.wanted.babdoduk.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "맛집")
@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantReviewService reviewService;
    private final RestaurantReviewStatService statService;

    @Operation(summary = "맛집 상세 조회")
    @GetMapping("/{id}")
    public ApiResponse<RestaurantDetailResponseDto> getRestaurant(@PathVariable Long id) {
        return ApiResponse.ok(
                RestaurantDetailResponseDto.of(
                        restaurantService.getRestaurant(id),
                        statService.getReviewRating(id),
                        reviewService.getReviews(id)
                )
        );
    }

}
