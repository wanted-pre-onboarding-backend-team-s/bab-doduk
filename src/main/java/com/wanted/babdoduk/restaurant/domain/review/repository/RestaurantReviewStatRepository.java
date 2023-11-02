package com.wanted.babdoduk.restaurant.domain.review.repository;

import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewStatRepository extends JpaRepository<RestaurantReviewStat, Long> {

}
