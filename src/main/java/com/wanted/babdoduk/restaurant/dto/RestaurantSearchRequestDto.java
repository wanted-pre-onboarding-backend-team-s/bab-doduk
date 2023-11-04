package com.wanted.babdoduk.restaurant.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantSearchRequestDto {

    private String keyword;
    private int page;
    private int size;

    public PageRequest of() {
        return PageRequest.of(page - 1, size);
    }
}
