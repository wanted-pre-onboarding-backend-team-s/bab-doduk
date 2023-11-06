package com.wanted.babdoduk.restaurant.domain.review.service;

import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewRequestDto;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewScore;
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

        return toReviewResponseDto(reviewRepository.save(
                RestaurantReview.builder()
                        .userId(userId)
                        .restaurantId(restaurantId)
                        .score(RestaurantReviewScore.values()[reviewRequestDto.getScore()])
                        .comment(reviewRequestDto.getComment())
                        .build()));
    }
    private void checkUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
    }

    private RestaurantReviewResponseDto toReviewResponseDto(RestaurantReview savedReview) {
        return RestaurantReviewResponseDto.builder()
                .reviewId(savedReview.getId())
                .userId(savedReview.getUserId())
                .restaurantId(savedReview.getRestaurantId())
                .score(savedReview.getScore().getValue())
                .comment(savedReview.getComment())
                .build();
    }
}
