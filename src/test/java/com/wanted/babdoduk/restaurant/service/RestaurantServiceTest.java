package com.wanted.babdoduk.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    Restaurant restaurant, closedRestaurant;

    @BeforeEach
    void setUp() {
        restaurant = Restaurant.builder()
            .manageNo("manage-no")
            .sigunName("sigun name 1")
            .sigunCode("sigun code 1")
            .bizName("biz name 1")
            .bizStatus("영업/정상")
            .cuisineType("중국식")
            .roadAddr("road address")
            .jibunAddr("jibun address")
            .latitude(new BigDecimal("0.0"))
            .longitude(new BigDecimal("0.0"))
            .build();

        closedRestaurant = Restaurant.builder()
            .manageNo("manage-no")
            .sigunName("sigun name 2")
            .sigunCode("sigun code 2")
            .bizName("biz name 2")
            .bizStatus("폐업")
            .cuisineType("중국식")
            .roadAddr("road address")
            .jibunAddr("jibun address")
            .latitude(new BigDecimal("0.0"))
            .longitude(new BigDecimal("0.0"))
            .build();
    }

    @DisplayName("맛집 상세 조회 성공")
    @Test
    void get_restaurant_detail_success() {

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getRestaurant(restaurant.getId());

        assertThat(result.getBizName()).isEqualTo("biz name 1");
        assertThat(result.getCuisineType()).isEqualTo("중국식");
    }

    @DisplayName("존재하지 않는 식당일 경우 조회 실패")
    @Test
    void not_found_restaurant_detail_fail() {
        when(restaurantRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.getRestaurant(any(Long.class)))
            .isInstanceOf(NotFoundRestaurantException.class);
    }

    @DisplayName("폐업한 식당일 경우 조회 실패")
    @Test
    void closed_restaurant_detail_fail() {
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(
            Optional.of(closedRestaurant));

        assertThatThrownBy(() -> restaurantService.getRestaurant(closedRestaurant.getId()))
            .isInstanceOf(ClosedRestaurantException.class);
    }

}