package com.wanted.babdoduk.batch.client;

import com.wanted.babdoduk.batch.RawRestaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawRestaurantRepository extends JpaRepository<RawRestaurant, Long> {

    Optional<RawRestaurant> findByManageNo(String mangeNo);
}
