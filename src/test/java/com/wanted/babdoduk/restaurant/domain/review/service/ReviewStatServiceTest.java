package com.wanted.babdoduk.restaurant.domain.review.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.ResultGetRestaurantReviewStatDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewStatServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantReviewRepository reviewRepository;

    @Mock
    private RestaurantReviewStatRepository reviewStatRepository;

    @InjectMocks
    private ReviewStatService reviewStatService;

    private static final Long RESTAURANT_ID = 1L;

    @DisplayName("리뷰상태 테이블 insert")
    @Test
    void createReview_ReviewStatInsert() {
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(true);
        when(reviewRepository.getReviewCountAndScoreAverage(RESTAURANT_ID))
                .thenAnswer(invocation -> ResultGetRestaurantReviewStatDto.builder()
                        .restaurantId(RESTAURANT_ID)
                        .averageScore(5.0)
                        .reviewCount(1)
                        .build());
        when(reviewStatRepository.findByRestaurantId(RESTAURANT_ID))
                .thenReturn(Optional.empty());

        reviewStatService.updateRestaurantReviewStat(RESTAURANT_ID);

        verify(reviewStatRepository, times(1))
                .save(any(RestaurantReviewStat.class));
    }

    @DisplayName("리뷰상태 테이블 update")
    @Test
    void createReview_ReviewStatUpdate() {
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(true);
        when(reviewRepository.getReviewCountAndScoreAverage(RESTAURANT_ID))
                .thenAnswer(invocation -> ResultGetRestaurantReviewStatDto.builder()
                        .restaurantId(RESTAURANT_ID)
                        .averageScore(4.0)
                        .reviewCount(1)
                        .build());
        when(reviewStatRepository.findByRestaurantId(RESTAURANT_ID))
                .thenReturn(Optional.of(
                        RestaurantReviewStat.builder()
                                .restaurantId(RESTAURANT_ID)
                                .averageScore(5.0)
                                .reviewCount(2)
                                .build()));

        reviewStatService.updateRestaurantReviewStat(RESTAURANT_ID);

        verify(reviewStatRepository, never())
                .save(any(RestaurantReviewStat.class));
    }

    @DisplayName("리뷰 상태 테이블 update - 리뷰가 삭제됐을 경우")
    @Test
    void updateReviewStatIfDeletedReview_withReview() {
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(true);
        when(reviewRepository.existsByRestaurantId(RESTAURANT_ID)).thenReturn(true);
        when(reviewRepository.getReviewCountAndScoreAverage(RESTAURANT_ID))
                .thenReturn(ResultGetRestaurantReviewStatDto.builder()
                        .restaurantId(RESTAURANT_ID)
                        .averageScore(3.5)
                        .reviewCount(2)
                        .build());
        when(reviewStatRepository.findByRestaurantId(RESTAURANT_ID))
                .thenReturn(Optional.of(
                        RestaurantReviewStat.builder()
                                .restaurantId(RESTAURANT_ID)
                                .averageScore(5.0)
                                .reviewCount(1)
                                .build()));

        reviewStatService.updateRestaurantReviewStatIfDeletedReview(RESTAURANT_ID);
    }
}
