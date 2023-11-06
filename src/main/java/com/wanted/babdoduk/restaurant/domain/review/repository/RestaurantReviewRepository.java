package com.wanted.babdoduk.restaurant.domain.review.repository;

import com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewStatDto;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    List<RestaurantReview> findAllByRestaurantIdOrderByCreatedAtDesc(Long id);

    @Query(value = "select new com.wanted.babdoduk.restaurant.domain.review.dto.RestaurantReviewStatDto ("
            + "r.restaurantId,"
            + " cast(count(r) as int),"
            + " truncate(sum(cast(r.score as double)) / count(r), 2))"
            + " from RestaurantReview r"
            + " where r.restaurantId = :restaurantId")
    RestaurantReviewStatDto getReviewCountAndScoreAverage(@Param("restaurantId") Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);
}
