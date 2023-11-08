package com.wanted.babdoduk.restaurant.domain.restaurant;

import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByManageNo(String manageNo);

    boolean existsByManageNo(String manageNo);
}
