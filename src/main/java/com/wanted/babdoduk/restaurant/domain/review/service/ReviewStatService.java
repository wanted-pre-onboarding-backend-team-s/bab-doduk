package com.wanted.babdoduk.restaurant.domain.review.service;

import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewStatDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewStatService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository reviewRepository;
    private final RestaurantReviewStatRepository reviewStatRepository;

    @Transactional
    public void updateRestaurantReviewStat(Long restaurantId) {

        checkRestaurantExists(restaurantId);

        RestaurantReviewStatDto getReviewStatDto =
                reviewRepository.getReviewCountAndScoreAverage(restaurantId);

        Optional<RestaurantReviewStat> getRestaurantReviewStat =
                reviewStatRepository.findByRestaurantId(restaurantId);

        if (getRestaurantReviewStat.isEmpty()) {
            reviewStatRepository.save(getReviewStatDto.toEntity());
        } else {
            getRestaurantReviewStat.get().changeAverageAndCount(getReviewStatDto);
        }
    }

    @Transactional
    public void updateRestaurantReviewStatIfDeletedReview(Long restaurantId) {

        RestaurantReviewStatDto getReviewStatDto;

        checkRestaurantExists(restaurantId);

        if (!reviewRepository.existsByRestaurantId(restaurantId)) {
            getReviewStatDto = new RestaurantReviewStatDto(restaurantId);
        } else {
            getReviewStatDto = reviewRepository.getReviewCountAndScoreAverage(restaurantId);
        }

        try {
            reviewStatRepository.findByRestaurantId(restaurantId)
                    .orElseThrow(NotFoundRestaurantException::new)
                    .changeAverageAndCount(getReviewStatDto);
        } catch (NotFoundRestaurantException e) {
            log.error("[" + e.getClass().getSimpleName() + "] ex",
                    "not found a restaurant. not modify the restaurant review status table.");
        }
    }

    private void checkRestaurantExists(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundRestaurantException();
        }
    }
}
