package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import static com.wanted.babdoduk.restaurant.domain.restaurant.entity.QRestaurant.restaurant;
import static com.wanted.babdoduk.restaurant.domain.review.entity.QRestaurantReviewStat.restaurantReviewStat;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.babdoduk.restaurant.domain.restaurant.enums.BusinessStatus;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<RestaurantListResponseDto> findAllBySearch(RestaurantSearchRequestDto request) {
        Pageable pageable = request.of();
        List<RestaurantListResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        RestaurantListResponseDto.class,
                        restaurant.manageNo,
                        restaurant.sigunName,
                        restaurant.sigunCode,
                        restaurant.bizName,
                        restaurant.bizStatus,
                        restaurant.cuisineType,
                        restaurant.roadAddr,
                        restaurant.jibunAddr,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.createdAt,
                        restaurant.updatedAt,
                        restaurantReviewStat.averageScore
                ))
                .from(restaurant)
                .leftJoin(restaurantReviewStat)
                .on(restaurant.id.eq(restaurantReviewStat.restaurantId))
                .where(verifyClosed())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    private BooleanExpression verifyClosed() {
        return restaurant.bizStatus.eq(BusinessStatus.Open.status);
    }
}
