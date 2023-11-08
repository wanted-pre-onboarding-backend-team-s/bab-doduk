package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import org.springframework.data.domain.Page;

public interface RestaurantRepositoryCustom {

    Page<RestaurantListResponseDto> findBySearch(RestaurantSearchRequestDto request);

}
