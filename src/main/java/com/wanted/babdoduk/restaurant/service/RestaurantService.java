package com.wanted.babdoduk.restaurant.service;

import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = findRestaurant(id);
        restaurant.verifyClosed();

        return restaurant;
    }

    private Restaurant findRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(NotFoundRestaurantException::new);
    }
}
