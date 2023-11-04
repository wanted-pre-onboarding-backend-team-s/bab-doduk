package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
