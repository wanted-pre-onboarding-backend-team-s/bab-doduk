package com.wanted.babdoduk.restaurant.domain.restaurant;


import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.babdoduk.batch.RawRestaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Restaurant 메서드 테스트")
class RestaurantTest {

    private final Restaurant expectedRestaurant = Restaurant.builder()
            .bizName("가게이름")
            .manageNo("관리번호")
            .longitude(new BigDecimal("126.9830506431"))
            .latitude(new BigDecimal("37.1711414651"))
            .sigunName("시군이름")
            .sigunCode("시군코드")
            .cuisineType("식당종류")
            .jibunAddr("지번주소")
            .roadAddr("도로명주소")
            .bizStatus("영업/정상")
            .build();


    @Test
    @DisplayName("성공(createFromRaw) : Raw Restaurant 로 부터 Restaurant 객체 생성")
    void createRestaurantFromRawData() {
        // when
        RawRestaurant rawRestaurant = RawRestaurant.builder()
                .name("가게이름")
                .manageNo("관리번호")
                .lon("126.9830506431")
                .lat("37.1711414651")
                .sigunNm("시군이름")
                .sigunCd("시군코드")
                .cuisineType("식당종류")
                .jibunAddr("지번주소")
                .roadAddr("도로명주소")
                .bsnStateNm("영업/정상")
                .build();

        Restaurant restaurant = Restaurant.createFromRaw(rawRestaurant);

        // then
        assertThat(restaurant).usingRecursiveComparison()
                .isEqualTo(expectedRestaurant);
    }

    @Test
    @DisplayName("성공(update) : Restaurant 업데이트")
    void updateFromRawData() {
        RawRestaurant rawRestaurant = RawRestaurant.builder()
                .name("가게이름")
                .manageNo("관리번호")
                .lon("126.9830506431")
                .lat("37.1711414651")
                .sigunNm("시군이름")
                .sigunCd("시군코드")
                .cuisineType("식당종류")
                .jibunAddr("지번주소111")
                .roadAddr("도로명주소111")
                .bsnStateNm("영업/정상")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .bizName("가게이름")
                .manageNo("관리번호")
                .longitude(new BigDecimal("126.9830506431"))
                .latitude(new BigDecimal("37.1711414651"))
                .sigunName("시군이름")
                .sigunCode("시군코드")
                .cuisineType("식당종류")
                .jibunAddr("지번주소")
                .roadAddr("도로명주소")
                .bizStatus("영업/정상")
                .build();

        // when
        restaurant.update(rawRestaurant);

        // then
        assertThat(restaurant.getJibunAddr()).isEqualTo("지번주소111");
        assertThat(restaurant.getRoadAddr()).isEqualTo("도로명주소111");
    }

}