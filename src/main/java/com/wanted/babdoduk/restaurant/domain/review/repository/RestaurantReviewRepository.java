package com.wanted.babdoduk.restaurant.domain.review.repository;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview,Long> {

}
