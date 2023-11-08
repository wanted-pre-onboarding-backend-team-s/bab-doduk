package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import com.wanted.babdoduk.user.domain.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface RestaurantRepositoryCustom {

    Page<RestaurantListResponseDto> findBySearch(RestaurantSearchRequestDto request);

    List<Restaurant> findRecommendedRestaurants(User user);

}
