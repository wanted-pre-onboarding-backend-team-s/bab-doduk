package com.wanted.babdoduk.restaurant.domain.review.entity;

import lombok.Getter;

@Getter
public enum RestaurantReviewScore {
    Zero(0), One(1), Two(2),
    Three(3), Four(4), Five(5);

    private final int value;

    RestaurantReviewScore(int value) {
        this.value = value;
    }

}
