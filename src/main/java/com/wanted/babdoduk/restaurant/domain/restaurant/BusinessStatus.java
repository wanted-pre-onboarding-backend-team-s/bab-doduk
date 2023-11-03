package com.wanted.babdoduk.restaurant.domain.restaurant;

import lombok.Getter;

@Getter
public enum BusinessStatus {
    Open("영업"),
    Close("폐업");

    public final String status;

    BusinessStatus(String status) {
        this.status = status;
    }
}
