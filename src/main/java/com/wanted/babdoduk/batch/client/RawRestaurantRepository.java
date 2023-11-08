package com.wanted.babdoduk.batch.client;

import com.wanted.babdoduk.batch.RawRestaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RawRestaurantRepository extends JpaRepository<RawRestaurant, Long> {

    Optional<RawRestaurant> findByManageNo(String mangeNo);

    @Query("select count(r.manageNo) from RawRestaurant r "
            + "where r.bsnStateNm in ('영업/정상', '폐업')"
            + " and r.sigunNm != null "
            + " and r.name != null "
            + " and r.sigunCd != null "
            + " and r.manageNo != null "
            + "and r.roadAddr != null "
            + "and r.jibunAddr != null "
            + "and r.lon != null "
            + "and r.lat != null "
            + "and r.cuisineType != null ")
    Long countAll();

    @Query("select r from RawRestaurant r "
            + "where r.bsnStateNm in ('영업/정상', '폐업')"
            + " and r.sigunNm != null"
            + " and r.name != null"
            + " and r.sigunCd != null"
            + " and r.manageNo != null "
            + "and r.roadAddr != null "
            + "and r.jibunAddr != null "
            + "and r.lon != null "
            + "and r.lat != null "
            + "and r.cuisineType != null")
    Page<RawRestaurant> getAll(Pageable pageable);

    @Override
    @Query("select r from RawRestaurant r "
            + "where r.bsnStateNm in ('영업/정상', '폐업')"
            + " and r.sigunNm != null"
            + " and r.sigunCd != null"
            + " and r.name != null "
            + " and r.manageNo != null "
            + "and r.roadAddr != null "
            + "and r.jibunAddr != null "
            + "and r.lon != null "
            + "and r.lat != null "
            + "and r.cuisineType != null")
    Page<RawRestaurant> findAll(Pageable pageable);
}
