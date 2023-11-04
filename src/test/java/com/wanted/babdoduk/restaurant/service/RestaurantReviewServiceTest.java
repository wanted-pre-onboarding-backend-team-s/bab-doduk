package com.wanted.babdoduk.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantReviewResponseDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantReviewServiceTest {

    @InjectMocks
    private RestaurantReviewService reviewService;

    @Mock
    private RestaurantReviewRepository reviewRepository;

    Restaurant restaurant;
    List<RestaurantReview> reviews = new ArrayList<>();

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
    }

    @DisplayName("맛집 리뷰 리스트 조회 성공")
    @Test
    void get_restaurant_review_success() {
        when(reviewRepository.findAllByRestaurantIdOrderByCreatedAtDesc(restaurant.getId())).thenReturn(reviews);

        List<RestaurantReviewResponseDto> result = reviewService.getReviews(restaurant.getId());

        assertThat(result.isEmpty()).isTrue();
    }

}