package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantReviewStatService {

    private final RestaurantReviewStatRepository statRepository;

    @Transactional(readOnly = true)
    public double getReviewRating(Long restaurantId) {
        return statRepository.findByRestaurantId(restaurantId).getAverageScore();
    }
}
