package com.wanted.babdoduk.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantDetailResponseDto;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private static final int ZERO = 0;

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantReviewRepository reviewRepository;

    @Mock
    private RestaurantReviewStatRepository statRepository;

    Restaurant restaurant, closedRestaurant;
    RestaurantReviewStat stat;
    List<RestaurantReview> reviews = new ArrayList<>();

    @BeforeEach
    void setUp() {
        restaurant = Restaurant.builder()
                               .sigunName("sigun name 1")
                               .bizName("biz name 1")
                               .bizStatus("영업")
                               .cuisineType("중국식")
                               .roadAddr("road address")
                               .jibunAddr("jibun address")
                               .latitude(new BigDecimal("0.0"))
                               .longitude(new BigDecimal("0.0"))
                               .build();

        closedRestaurant = Restaurant.builder()
                                     .sigunName("sigun name 2")
                                     .bizName("biz name 2")
                                     .bizStatus("폐업")
                                     .cuisineType("중국식")
                                     .roadAddr("road address")
                                     .jibunAddr("jibun address")
                                     .latitude(new BigDecimal("0.0"))
                                     .longitude(new BigDecimal("0.0"))
                                     .build();

        stat = RestaurantReviewStat.builder()
                                   .restaurantId(restaurant.getId())
                                   .averageScore(ZERO)
                                   .reviewCount(ZERO)
                                   .build();
    }

    @DisplayName("맛집 상세 조회 성공")
    @Test
    void get_restaurant_detail_success() {

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(reviewRepository.findAllByRestaurantId(restaurant.getId())).thenReturn(reviews);
        when(statRepository.findByRestaurantId(restaurant.getId())).thenReturn(stat);

        RestaurantDetailResponseDto result = restaurantService.getRestaurant(restaurant.getId());

        assertThat(result.getBizName()).isEqualTo("biz name 1");
        assertThat(result.getReviews().isEmpty()).isTrue();
        assertThat(result.getStat()).isEqualTo(ZERO);

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
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(closedRestaurant));

        assertThatThrownBy(() -> restaurantService.getRestaurant(closedRestaurant.getId()))
                .isInstanceOf(ClosedRestaurantException.class);

    }
}