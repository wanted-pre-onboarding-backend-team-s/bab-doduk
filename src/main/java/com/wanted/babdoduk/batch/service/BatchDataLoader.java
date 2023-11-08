package com.wanted.babdoduk.batch.service;

import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "batch.init-load.enabled", havingValue = "true")
public class BatchDataLoader implements CommandLineRunner {

    public static final int INIT_SIZE = 0;

    private final OpenRestaurantService openRestaurantService;
    private final RawRestaurantRepository rawRestaurantRepository;
    private final RestaurantDataSyncService restaurantDataSyncService;
    private final RestaurantRepository restaurantRepository;


    @Override
    public void run(String... args) throws Exception {
        if (rawRestaurantRepository.count() == INIT_SIZE) {
            System.out.println("API -> RAW_DATA 저장 및 업데이트 배치 작업 시작합니다.");
            openRestaurantService.initSave();
            System.out.println("API -> RAW_DATA 저장 및 업데이트 배치 작업이 종료되었습니다.");
        }

        if (restaurantRepository.count() == INIT_SIZE) {
            System.out.println("RAW_DATA -> 실제 관리하는 테이블로 저장 및 업데이트 배치 작업 시작합니다.");
            restaurantDataSyncService.initSaveRestaurantDataFromRawRestaurants();
            System.out.println("RAW_DATA -> 실제 관리하는 테이블로 저장 및 업데이트 배치 작업이 종료되었습니다.");
        }
    }
}
