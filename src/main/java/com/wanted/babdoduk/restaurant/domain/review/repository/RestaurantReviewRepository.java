package com.wanted.babdoduk.restaurant.domain.review.repository;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    List<RestaurantReview> findAllByRestaurantId(Long id);
}
