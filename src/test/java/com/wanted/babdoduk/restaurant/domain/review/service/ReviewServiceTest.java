package com.wanted.babdoduk.restaurant.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewScore;
import com.wanted.babdoduk.restaurant.domain.review.exception.ReviewNotFoundException;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService restaurantReviewService;

    private static final Long USER_ID = 1L;
    private static final Long RESTAURANT_ID = 1L;
    private static final Long REVIEW_ID = 1L;

    @DisplayName("리뷰 작성 성공")
    @Test
    void createReview_insert() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(true);
        when(reviewRepository.save(Mockito.any(RestaurantReview.class)))
                .thenAnswer(invocation -> RestaurantReview.builder()
                        .userId(USER_ID)
                        .restaurantId(RESTAURANT_ID)
                        .score(RestaurantReviewScore.Five)
                        .comment("코멘트").build());

        RestaurantReviewResponseDto response = restaurantReviewService.createRestaurantReview(
                USER_ID, RESTAURANT_ID, getRestaurantReviewRequestDto());

        assertThat(response.getScore()).isEqualTo(5);
        assertThat(response.getComment()).isEqualTo("코멘트");
    }

    @DisplayName("리뷰 작성 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedCreateReview_notFoundUserException() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> restaurantReviewService.createRestaurantReview(
                USER_ID, RESTAURANT_ID, getRestaurantReviewRequestDto()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 작성 실패 - 해당 식당을 찾을 수 없는 경우")
    @Test
    void failedCreateReview_notFoundRestaurantException() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(restaurantRepository.existsById(RESTAURANT_ID)).thenReturn(false);
        assertThatThrownBy(() -> restaurantReviewService.createRestaurantReview(
                USER_ID, RESTAURANT_ID, getRestaurantReviewRequestDto()))
                .isInstanceOf(NotFoundRestaurantException.class);
    }

    @DisplayName("리뷰 수정 성공")
    @Test
    void updateReview() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);

        when(reviewRepository.findById(REVIEW_ID))
                .thenReturn(Optional.of(
                        RestaurantReview.builder()
                                .userId(USER_ID)
                                .restaurantId(RESTAURANT_ID)
                                .score(RestaurantReviewScore.Five)
                                .comment("코멘트").build()));

        RestaurantReview restaurantReview = restaurantReviewService.updateRestaurantReview(
                USER_ID,
                REVIEW_ID,
                RestaurantReviewRequestDto.builder()
                        .score(4)
                        .comment("수정된 코멘트")
                        .build()
        );

        assertThat(restaurantReview.getScore()).isEqualTo(RestaurantReviewScore.Four);
        assertThat(restaurantReview.getComment()).isEqualTo("수정된 코멘트");
    }

    @DisplayName("리뷰 수정 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedUpdateReview_userNotFoundException() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> restaurantReviewService.updateRestaurantReview(
                USER_ID, RESTAURANT_ID, getRestaurantReviewRequestDto()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 수정 실패 - 해당 리뷰를 찾을 수 없는 경우")
    @Test
    void failedUpdateReview_reviewNotFoundException() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(reviewRepository.findById(REVIEW_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> restaurantReviewService.updateRestaurantReview(
                USER_ID, RESTAURANT_ID, getRestaurantReviewRequestDto()))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    @DisplayName("리뷰 삭제 성공")
    @Test
    void deleteReview() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(reviewRepository.existsById(USER_ID)).thenReturn(true);
        restaurantReviewService.deleteRestaurantReview(USER_ID, REVIEW_ID);

        verify(reviewRepository).deleteById(REVIEW_ID);
    }

    @DisplayName("리뷰 삭제 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedDeleteReview_userNotFoundException() {
        when(userRepository.existsById(USER_ID)).thenReturn(false);
        assertThatThrownBy(() -> restaurantReviewService
                .deleteRestaurantReview(USER_ID, REVIEW_ID))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 삭제 실패 - 해당 리뷰를 찾을 수 없는 경우")
    @Test
    void failedDeleteReview_reviewNotFoundException() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        when(reviewRepository.existsById(REVIEW_ID)).thenReturn(false);
        assertThatThrownBy(() -> restaurantReviewService
                .deleteRestaurantReview(USER_ID, REVIEW_ID))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    private RestaurantReviewRequestDto getRestaurantReviewRequestDto() {
        return RestaurantReviewRequestDto.builder()
                .score(5)
                .comment("리뷰 코멘트")
                .build();
    }
}
