package com.wanted.babdoduk.restaurant.dto;

import com.wanted.babdoduk.restaurant.domain.restaurant.enums.SortType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class RestaurantSearchRequestDto {

    @NotBlank(message = "필수 입력값 입니다.")
    private String latitude;

    @NotBlank(message = "필수 입력값 입니다.")
    private String longitude;

    @NotNull(message = "필수 입력값 입니다.")
    private double range;

    private String keyword;
    private int page;
    private int size;
    private SortType sort;

    protected RestaurantSearchRequestDto() {
        page = 1;
        size = 10;
        sort = SortType.DISTANCE;
    }

    public static RestaurantSearchRequestDto create() {
        return new RestaurantSearchRequestDto();
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size);
    }
}
