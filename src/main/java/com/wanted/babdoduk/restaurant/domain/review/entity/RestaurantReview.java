package com.wanted.babdoduk.restaurant.domain.review.entity;

import com.wanted.babdoduk.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "restaurant_reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long restaurantId;
    private RestaurantReviewScore score;

    @Lob
    private String comment;

    @Builder
    public RestaurantReview(
            Long userId, Long restaurantId,
            RestaurantReviewScore score, String comment) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.comment = comment;
    }

    public void changeScoreAndComment(int score, String comment) {
        this.score = RestaurantReviewScore.of(score);
        this.comment = comment;
    }
}
