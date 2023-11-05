package com.wanted.babdoduk.restaurant.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.restaurant.dto.RestaurantDetailResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import com.wanted.babdoduk.restaurant.service.RestaurantReviewService;
import com.wanted.babdoduk.restaurant.service.RestaurantReviewStatService;
import com.wanted.babdoduk.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Operation(summary = "맛집 목록 조회")
    @GetMapping()
    public ApiResponse<Page<RestaurantListResponseDto>> getRestaurants(
            @Valid @ParameterObject @ModelAttribute RestaurantSearchRequestDto request
    ) {
        return ApiResponse.ok(restaurantService.getRestaurants(request));
    }

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
