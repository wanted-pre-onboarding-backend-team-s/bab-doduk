package com.wanted.babdoduk.restaurant.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.restaurant.dto.RestaurantDetailResponseDto;
import com.wanted.babdoduk.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "맛집 상세 조회")
    @GetMapping("/{id}")
    public ApiResponse<RestaurantDetailResponseDto> getRestaurant(@PathVariable Long id) {
        return ApiResponse.toResponse(restaurantService.getRestaurant(id));
    }

}
