package com.wanted.babdoduk.restaurant.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.babdoduk.restaurant.domain.restaurant.BusinessStatus;
import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewScore;
import com.wanted.babdoduk.restaurant.domain.review.exception.ReviewNotFoundException;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantReviewRepository reviewRepository;

    @Autowired
    private RestaurantReviewStatRepository reviewStatRepository;

    @Autowired
    private ReviewService restaurantReviewService;

    private Long userId;

    private Long restaurantId;

    private Long reviewId;

    @BeforeEach
    void init() {
        reviewStatRepository.deleteAll();
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();

        userId = userRepository.save(
                User.builder()
                        .username("계정명")
                        .encodedPassword("비밀번호")
                        .latitude(BigDecimal.valueOf(37.512468))
                        .longitude(BigDecimal.valueOf(127.102568))
                        .lunchPushApproved(true)
                        .build())
                .toCreateUserResponseDto().userId();

        restaurantId = restaurantRepository.save(
                Restaurant.builder()
                        .manageNo("1")
                        .sigunName("시군구")
                        .bizName("업체명")
                        .bizStatus(BusinessStatus.Open.getStatus())
                        .cuisineType("업태")
                        .roadAddr("도로명주소")
                        .jibunAddr("지번주소")
                        .latitude(BigDecimal.valueOf(37.514537))
                        .longitude(BigDecimal.valueOf(127.100134))
                        .build())
                .getId();

        reviewId = reviewRepository.save(
                RestaurantReview.builder()
                        .userId(userId)
                        .restaurantId(restaurantId)
                        .score(RestaurantReviewScore.Five)
                        .comment("리뷰")
                        .build())
                .getId();
    }

    @DisplayName("리뷰 작성 성공")
    @Test
    void createReview_insert() {
        restaurantReviewService.createRestaurantReview(
                userId, restaurantId, getRestaurantReviewRequestDto());
        assertThat(reviewRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("리뷰 작성 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedCreateReview_notFoundUserException() {
        assertThatThrownBy(() -> restaurantReviewService.createRestaurantReview(
                userId + 1,  restaurantId, getRestaurantReviewRequestDto()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 작성 실패 - 해당 식당을 찾을 수 없는 경우")
    @Test
    void failedCreateReview_notFoundRestaurantException() {
        assertThatThrownBy(() -> restaurantReviewService.createRestaurantReview(
                userId, restaurantId + 1, getRestaurantReviewRequestDto()))
                .isInstanceOf(NotFoundRestaurantException.class);
    }

    @DisplayName("리뷰 수정 성공")
    @Test
    void updateReview() {
        restaurantReviewService.updateRestaurantReview(
                userId, reviewId, getRestaurantReviewRequestDto(4, "리뷰 수정"));
        assertThat(reviewRepository.findById(reviewId).get().getScore())
                .isEqualTo(RestaurantReviewScore.Four);
        assertThat(reviewRepository.findById(reviewId).get().getComment())
                .isEqualTo("리뷰 수정");
    }

    @DisplayName("리뷰 수정 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedUpdateReview_userNotFoundException() {
        assertThatThrownBy(() -> restaurantReviewService.updateRestaurantReview(
                userId + 1,  reviewId, getRestaurantReviewRequestDto()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 수정 실패 - 해당 리뷰를 찾을 수 없는 경우")
    @Test
    void failedUpdateReview_reviewNotFoundException() {
        assertThatThrownBy(() -> restaurantReviewService.updateRestaurantReview(
                userId, reviewId + 1, getRestaurantReviewRequestDto()))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    @DisplayName("리뷰 삭제 성공")
    @Test
    void deleteReview() {
        restaurantReviewService.deleteRestaurantReview(userId, reviewId);
        assertThat(reviewRepository.existsById(reviewId)).isFalse();
    }

    @DisplayName("리뷰 삭제 실패 - 해당 유저를 찾을 수 없는 경우")
    @Test
    void failedDeleteReview_userNotFoundException() {
        assertThatThrownBy(() -> restaurantReviewService.deleteRestaurantReview(
                userId + 1, reviewId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName("리뷰 삭제 실패 - 해당 리뷰를 찾을 수 없는 경우")
    @Test
    void failedDeleteReview_reviewNotFoundException() {
        assertThatThrownBy(() -> restaurantReviewService.deleteRestaurantReview(
                userId, reviewId + 1))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    private RestaurantReviewRequestDto getRestaurantReviewRequestDto() {
        return RestaurantReviewRequestDto.builder()
                .score(5)
                .comment("리뷰 코멘트")
                .build();
    }

    private RestaurantReviewRequestDto getRestaurantReviewRequestDto(int score, String comment) {
        return RestaurantReviewRequestDto.builder()
                .score(score)
                .comment(comment)
                .build();
    }
}