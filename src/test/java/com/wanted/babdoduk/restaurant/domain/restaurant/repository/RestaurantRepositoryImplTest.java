package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.batch.service.OpenRestaurantService;
import com.wanted.babdoduk.common.config.querydsl.QueryDslConfig;
import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.enums.SortType;
import com.wanted.babdoduk.restaurant.domain.review.entity.RestaurantReviewStat;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
@MockBean(OpenRestaurantService.class)
class RestaurantRepositoryImplTest {

    private static final String MY_LATITUDE = "37.397569";
    private static final String MY_LONGITUDE = "126.976529";
    private static final String CHINESE_FOOD = "중국식";
    private static final String KOREAN_FOOD = "한식";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

    Restaurant restaurant1, restaurant2;
    RestaurantReviewStat stat1, stat2;

    @BeforeEach
    void setUp() {
        restaurant1 = Restaurant.builder()
                                .manageNo("manage-no")
                                .sigunName("sigun name 1")
                                .sigunCode("sigun code 1")
                                .bizName("biz name 1")
                                .bizStatus("영업/정상")
                                .cuisineType("중국식")
                                .roadAddr("road address")
                                .jibunAddr("jibun address")
                                .latitude(new BigDecimal("37.403581"))
                                .longitude(new BigDecimal("126.946761"))
                                .build();

        entityManager.persist(restaurant1);

        stat1 = RestaurantReviewStat.builder()
                                    .restaurantId(restaurant1.getId())
                                    .averageScore(4.33)
                                    .reviewCount(3)
                                    .build();

        entityManager.persist(stat1);

        restaurant2 = Restaurant.builder()
                                .manageNo("manage-no")
                                .sigunName("sigun name 2")
                                .sigunCode("sigun code 2")
                                .bizName("biz name 2")
                                .bizStatus("영업/정상")
                                .cuisineType("한식")
                                .roadAddr("road address")
                                .jibunAddr("jibun address")
                                .latitude(new BigDecimal("37.401690"))
                                .longitude(new BigDecimal("126.974500"))
                                .build();

        entityManager.persist(restaurant2);

        stat2 = RestaurantReviewStat.builder()
                                    .restaurantId(restaurant2.getId())
                                    .averageScore(3.51)
                                    .reviewCount(2)
                                    .build();

        entityManager.persist(stat2);
    }

    @DisplayName("맛집 목록 조회 성공")
    @Test
    void get_restaurant_list_success() {
        RestaurantSearchRequestDto request = RestaurantSearchRequestDto.create();
        request.setLatitude(MY_LATITUDE);
        request.setLongitude(MY_LONGITUDE);
        request.setRange(3.0);

        Page<RestaurantListResponseDto> result = restaurantRepository.findBySearch(request);

        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @DisplayName("range = 1.0(1000km) 이내 맛집 목록 조회 성공")
    @Test
    void get_restaurant_list_within_range_success() {
        RestaurantSearchRequestDto request = RestaurantSearchRequestDto.create();
        request.setLatitude(MY_LATITUDE);
        request.setLongitude(MY_LONGITUDE);
        request.setRange(1.0);

        Page<RestaurantListResponseDto> result = restaurantRepository.findBySearch(request);

        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @DisplayName("식당명/식당타입에 키워드 포함 맛집 목록 조회 성공")
    @Test
    void get_restaurant_list_contain_keyword_success() {
        RestaurantSearchRequestDto request = RestaurantSearchRequestDto.create();
        request.setLatitude(MY_LATITUDE);
        request.setLongitude(MY_LONGITUDE);
        request.setRange(1.0);
        request.setKeyword(KOREAN_FOOD);

        Page<RestaurantListResponseDto> result = restaurantRepository.findBySearch(request);

        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).getCuisineType()).isEqualTo(KOREAN_FOOD);
    }

    @DisplayName("거리순(기본)으로 맛집 목록 조회 성공")
    @Test
    void get_restaurant_list_orderBy_distance_success() {
        RestaurantSearchRequestDto request = RestaurantSearchRequestDto.create();
        request.setLatitude(MY_LATITUDE);
        request.setLongitude(MY_LONGITUDE);
        request.setRange(3.0);

        Page<RestaurantListResponseDto> result = restaurantRepository.findBySearch(request);

        assertThat(result.getContent().get(0).getCuisineType()).isEqualTo(KOREAN_FOOD);
        assertThat(result.getContent().get(1).getCuisineType()).isEqualTo(CHINESE_FOOD);
    }

    @DisplayName("평점순으로 맛집 목록 조회 성공")
    @Test
    void get_restaurant_list_orderBy_rating_success() {
        RestaurantSearchRequestDto request = RestaurantSearchRequestDto.create();
        request.setLatitude(MY_LATITUDE);
        request.setLongitude(MY_LONGITUDE);
        request.setRange(3.0);
        request.setSort(SortType.RATING);

        Page<RestaurantListResponseDto> result = restaurantRepository.findBySearch(request);

        assertThat(result.getContent().get(0).getCuisineType()).isEqualTo(CHINESE_FOOD);
        assertThat(result.getContent().get(1).getCuisineType()).isEqualTo(KOREAN_FOOD);
    }

}