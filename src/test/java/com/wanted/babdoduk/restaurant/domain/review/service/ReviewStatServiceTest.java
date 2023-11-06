package com.wanted.babdoduk.restaurant.domain.review.service;


import com.wanted.babdoduk.restaurant.domain.restaurant.BusinessStatus;
import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewScore;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import com.wanted.babdoduk.user.domain.entity.User;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReviewStatServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantReviewRepository reviewRepository;

    @Autowired
    private RestaurantReviewStatRepository reviewStatRepository;

    @Autowired
    private ReviewStatService reviewStatService;

    private Long restaurantId;

    private Long userId;

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
    }

    @DisplayName("리뷰상태 테이블 insert")
    @Test
    void createReview_insert() {
        createdReview(RestaurantReviewScore.Five);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getReviewCount()).isEqualTo(1);
        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getAverageScore()).isEqualTo(5);
    }

    @DisplayName("리뷰상태 테이블 update")
    @Test
    void createReview_update() {
        createdReview(RestaurantReviewScore.Five);
        createdReview(RestaurantReviewScore.One);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getReviewCount()).isEqualTo(2);
        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getAverageScore()).isEqualTo(3);
    }

    @DisplayName("리뷰상태 테이블 update - 리뷰가 삭제됐을 경우 (리뷰 테이블에 해당 음식점의 리뷰가 남아있을 경우)")
    @Test
    void creatReview_update_ifDeletedReview_reviewsIsNotEmpty() {
        Long reviewId = createdReview(RestaurantReviewScore.One);
        createdReview(RestaurantReviewScore.Five);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        reviewRepository.deleteById(reviewId);
        reviewStatService.updateRestaurantReviewStatIfDeletedReview(restaurantId);

        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getReviewCount()).isEqualTo(1);
        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getAverageScore()).isEqualTo(5);
    }

    @DisplayName("리뷰상태 테이블 update - 리뷰가 삭제됐을 경우 (리뷰 테이블에 해당 음식점의 리뷰가 없을 경우)")
    @Test
    void creatReview_update_ifDeletedReview_reviewsIsEmpty() {
        Long reviewId = createdReview(RestaurantReviewScore.One);
        reviewStatService.updateRestaurantReviewStat(restaurantId);

        reviewRepository.deleteById(reviewId);
        reviewStatService.updateRestaurantReviewStatIfDeletedReview(restaurantId);

        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getReviewCount()).isEqualTo(0);
        Assertions.assertThat(getGetRestaurantReviewStat(restaurantId).getAverageScore()).isEqualTo(0.0);
    }

    private RestaurantReviewStat getGetRestaurantReviewStat(Long restaurantId) {
        return reviewStatRepository.findByRestaurantId(restaurantId).get();
    }

    private Long createdReview (RestaurantReviewScore reviewScore) {
        return reviewRepository.save(
                RestaurantReview.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .score(reviewScore)
                .comment("리뷰 코멘트")
                .build()).getId();
    }
}