package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewRepository;
import com.wanted.babdoduk.restaurant.domain.review.repository.RestaurantReviewStatRepository;
import com.wanted.babdoduk.restaurant.dto.RestaurantDetailResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantReviewResponseDto;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final String CLOSE_STATUS = "폐업";

    private final RestaurantRepository restaurantRepository;
    private final RestaurantReviewRepository reviewRepository;
    private final RestaurantReviewStatRepository statRepository;

    @Transactional(readOnly = true)
    public RestaurantDetailResponseDto getRestaurant(Long id) {
        Restaurant restaurant = findRestaurant(id);
        checkBusinessStatus(restaurant);

        return RestaurantDetailResponseDto.of(
                restaurant,
                statRepository.findByRestaurantId(id).getAverageScore(),
                reviewRepository.findAllByRestaurantIdOrderByCreatedAtDesc(id)
                                .stream()
                                .map(RestaurantReviewResponseDto::of)
                                .collect(Collectors.toList()));
    }

    private void checkBusinessStatus(Restaurant restaurant) {
        if (restaurant.getBizStatus().equals(CLOSE_STATUS)) {
            throw new ClosedRestaurantException();
        }
    }

    private Restaurant findRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(NotFoundRestaurantException::new);
    }
}
