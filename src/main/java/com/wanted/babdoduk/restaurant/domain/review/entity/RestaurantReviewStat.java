package com.wanted.babdoduk.restaurant.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "restaurant_review_stats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantReviewStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long restaurantId;
    private double averageScore;
    private int reviewCount;

    @Builder
    public RestaurantReviewStat(Long restaurantId, double averageScore, int reviewCount) {
        this.restaurantId = restaurantId;
        this.averageScore = averageScore;
        this.reviewCount = reviewCount;
    }

}
