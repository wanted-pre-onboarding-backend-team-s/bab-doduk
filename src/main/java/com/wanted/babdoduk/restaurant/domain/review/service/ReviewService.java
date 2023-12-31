package com.wanted.babdoduk.restaurant.domain.review.service;

import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.exception.ReviewNotFoundException;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import com.wanted.babdoduk.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantReviewResponseDto createRestaurantReview(
            Long userId,
            Long restaurantId,
            RestaurantReviewRequestDto reviewRequestDto) {

        checkUserExists(userId);

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundRestaurantException();
        }

        return new RestaurantReviewResponseDto(
                reviewRepository.save(reviewRequestDto.toEntity(userId, restaurantId)));
    }

    @Transactional
    public RestaurantReview updateRestaurantReview(
            Long userId, Long reviewId, RestaurantReviewRequestDto reviewRequestDto) {

        checkUserExists(userId);

        RestaurantReview savedRestaurantReview =
                reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        savedRestaurantReview.changeScoreAndComment(
                reviewRequestDto.getScore(), reviewRequestDto.getComment());

        return savedRestaurantReview;
    }

    @Transactional
    public void deleteRestaurantReview(Long userId, Long reviewId) {

        checkUserExists(userId);

        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException();
        }

        reviewRepository.deleteById(reviewId);
    }

    private void checkUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
    }
}
