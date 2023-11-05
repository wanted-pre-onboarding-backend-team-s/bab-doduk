package com.wanted.babdoduk.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantReviewStatServiceTest {

    private static final int ZERO = 0;

    @InjectMocks
    private RestaurantReviewStatService statService;

    @Mock
    private RestaurantReviewStatRepository statRepository;

    Restaurant restaurant;
    RestaurantReviewStat stat;

    @BeforeEach
    void setUp() {
        restaurant = Restaurant.builder()
                               .manageNo("manage-no")
                               .sigunName("sigun name 1")
                               .sigunCode("sigun code 1")
                               .bizName("biz name 1")
                               .bizStatus("영업")
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

    @DisplayName("평점 조회 성공")
    @Test
    void get_restaurant_rating_success() {

        when(statRepository.findByRestaurantId(restaurant.getId())).thenReturn(stat);

        double result = statService.getReviewRating(restaurant.getId());

        assertThat(result).isEqualTo(ZERO);
    }

}