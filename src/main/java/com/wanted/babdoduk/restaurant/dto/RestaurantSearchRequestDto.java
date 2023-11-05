package com.wanted.babdoduk.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class RestaurantSearchRequestDto {

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;

    @NotNull
    private double range;

    private String keyword;
    private int page;
    private int size;

    protected RestaurantSearchRequestDto() {
        page = 1;
        size = 10;
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size);
    }
}
