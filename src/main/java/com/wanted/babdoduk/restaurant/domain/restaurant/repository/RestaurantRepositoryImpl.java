package com.wanted.babdoduk.restaurant.domain.restaurant.repository;

import static com.wanted.babdoduk.restaurant.domain.restaurant.entity.QRestaurant.restaurant;
import static com.wanted.babdoduk.restaurant.domain.review.entity.QRestaurantReviewStat.restaurantReviewStat;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.babdoduk.restaurant.domain.restaurant.entity.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.enums.BusinessStatus;
import com.wanted.babdoduk.restaurant.dto.RestaurantListResponseDto;
import com.wanted.babdoduk.restaurant.dto.RestaurantSearchRequestDto;
import com.wanted.babdoduk.user.domain.entity.User;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<RestaurantListResponseDto> findBySearch(RestaurantSearchRequestDto condition) {
        Pageable pageable = condition.of();

        List<RestaurantListResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        RestaurantListResponseDto.class,
                        restaurant.id,
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
                        restaurantReviewStat.averageScore))
                .from(restaurant)
                .leftJoin(restaurantReviewStat)
                .on(restaurant.id.eq(restaurantReviewStat.restaurantId))
                .where(closedEq(),
                       distanceLoe(condition.getLatitude(),
                                   condition.getLongitude(),
                                   condition.getRange()),
                       keywordCt(condition.getKeyword()))
                .orderBy(createOrderSpecifier(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Restaurant> countQuery = jpaQueryFactory
                .selectFrom(restaurant)
                .where(closedEq(),
                       distanceLoe(condition.getLatitude(),
                                   condition.getLongitude(),
                                   condition.getRange()),
                       keywordCt(condition.getKeyword()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Restaurant findByManageNo(String manageNo) {
        return jpaQueryFactory
                .selectFrom(restaurant)
                .where(restaurant.manageNo.eq(manageNo))
                .fetchFirst();
    }
  
    @Override
    public Boolean existsByManageNo(String manageNo) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(restaurant)
                .where(restaurant.manageNo.eq(manageNo))
                .fetchFirst();
        return fetchOne != null;
    }

    public List<Restaurant> findRecommendedRestaurants(User user) {
        return jpaQueryFactory.selectFrom(restaurant)
            .leftJoin(restaurantReviewStat)
            .on(restaurant.id.eq(restaurantReviewStat.restaurantId))
            .where(closedEq(), reviewScoreBetweenFourAndFive())
            .orderBy(getDistance(user.getLatitude(), user.getLongitude()).asc())
            .limit(5)
            .fetch();
    }

    private BooleanExpression keywordCt(String keyword) {
        if (StringUtils.isNullOrEmpty(keyword)) {
            return Expressions.TRUE;
        }

        return restaurant.bizName.contains(keyword)
                                 .or(restaurant.cuisineType.contains(keyword));
    }

    private BooleanExpression closedEq() {
        return restaurant.bizStatus.eq(BusinessStatus.Open.status);
    }

    private BooleanExpression reviewScoreBetweenFourAndFive() {
        return restaurantReviewStat.averageScore.between(4, 5);
    }

    private BooleanExpression distanceLoe(String latitude, String longitude, double range) {
        NumberExpression<BigDecimal> distance = getDistance(new BigDecimal(latitude), new BigDecimal(longitude));
        return distance.loe(range);
    }

    private static NumberExpression<BigDecimal> getDistance(BigDecimal latitude, BigDecimal longitude) {

        NumberExpression<BigDecimal> radiansLat =
                Expressions.numberTemplate(BigDecimal.class, "radians({0})", latitude);

        NumberExpression<BigDecimal> cosLat =
                Expressions.numberTemplate(BigDecimal.class, "cos({0})", radiansLat);
        NumberExpression<BigDecimal> cosRestaurantLat =
                Expressions.numberTemplate(BigDecimal.class, "cos(radians({0}))", restaurant.latitude);

        NumberExpression<BigDecimal> sinLat =
                Expressions.numberTemplate(BigDecimal.class, "sin({0})", radiansLat);
        NumberExpression<BigDecimal> sinRestaurantLat =
                Expressions.numberTemplate(BigDecimal.class, "sin(radians({0}))", restaurant.latitude);

        NumberExpression<BigDecimal> cosLon =
                Expressions.numberTemplate(BigDecimal.class, "cos(radians({0}) - radians({1}))",
                                           restaurant.longitude, longitude);

        NumberExpression<BigDecimal> acosExpression =
                Expressions.numberTemplate(BigDecimal.class, "acos({0})",
                                           cosLat.multiply(cosRestaurantLat)
                                                 .multiply(cosLon)
                                                 .add(sinLat.multiply(sinRestaurantLat)));

        return Expressions.numberTemplate(BigDecimal.class, "6371 * {0}", acosExpression);
    }

    private OrderSpecifier<?> createOrderSpecifier(RestaurantSearchRequestDto condition) {
        return switch (condition.getSort()) {
            case DISTANCE -> new OrderSpecifier<>(
                    Order.ASC, getDistance(new BigDecimal(condition.getLatitude()),
                                           new BigDecimal(condition.getLongitude())));
            case RATING -> new OrderSpecifier<>(Order.DESC, restaurantReviewStat.averageScore);
        };
    }

}
