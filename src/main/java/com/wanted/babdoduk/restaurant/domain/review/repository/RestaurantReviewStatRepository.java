package com.wanted.babdoduk.restaurant.domain.review.repository;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewStatRepository extends JpaRepository<RestaurantReviewStat, Long> {

    Optional<RestaurantReviewStat> findByRestaurantId(Long id);
}
