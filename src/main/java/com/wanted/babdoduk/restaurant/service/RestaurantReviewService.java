package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantReviewResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantReviewService {

    private final RestaurantReviewRepository reviewRepository;

    @Cacheable(value = "review", key = "#restaurantId")
    @Transactional(readOnly = true)
    public List<RestaurantReviewResponseDto> getReviews(Long restaurantId) {
        return reviewRepository.findAllByRestaurantIdOrderByCreatedAtDesc(restaurantId)
                               .stream()
                               .map(RestaurantReviewResponseDto::of)
                               .collect(Collectors.toList());
    }
}
