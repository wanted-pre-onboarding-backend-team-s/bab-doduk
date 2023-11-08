package com.wanted.babdoduk.batch.service;

import com.wanted.babdoduk.batch.RawRestaurant;
import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.restaurant.domain.restaurant.Restaurant;
import com.wanted.babdoduk.restaurant.domain.restaurant.RestaurantRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RestaurantDataSyncService {

    private static final long SIZE_PER_PAGE = 5000;
    private static final int POOL_SIZE = 32;
    private final RestaurantRepository restaurantRepository;
    private final RawRestaurantRepository rawRestaurantRepository;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);


    public RestaurantDataSyncService(RestaurantRepository restaurantRepository,
            RawRestaurantRepository rawRestaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.rawRestaurantRepository = rawRestaurantRepository;
    }

    @Transactional
    public void initSaveRestaurantDataFromRawRestaurants() throws InterruptedException {
        final long totalCount = rawRestaurantRepository.countAll();
        int lastPage = (int) (Math.ceil((double) totalCount / SIZE_PER_PAGE));
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        CountDownLatch latch = new CountDownLatch(lastPage);
        int currentPage = 1;
        while (currentPage <= lastPage) {
            final Pageable pageable = PageRequest.of(currentPage, (int) SIZE_PER_PAGE);
            executorService.submit(() -> {
                try {
                    Page<RawRestaurant> rawRestaurants = findAll(pageable);
                    restaurantRepository.saveAll(
                            rawRestaurants.stream()
                                    .map(Restaurant::createFromRaw)
                                    .toList()
                    );
                    printLog(pageable, rawRestaurants);
                } catch (Exception e) {
                    printErrLog(pageable);
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
            currentPage++;
        }
        latch.await();
    }

    @Transactional
    public void updateOrSaveRestaurantDataFromRawRestaurants() throws InterruptedException {
        final long totalCount = rawRestaurantRepository.countAll();
        int pageCount = (int) (Math.ceil((double) totalCount / SIZE_PER_PAGE));
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        CountDownLatch latch = new CountDownLatch(pageCount);
        int i = 1;
        while (i <= pageCount) {
            final Pageable pageable = PageRequest.of(i, (int) SIZE_PER_PAGE);
            executorService.submit(() -> {
                try {
                    Page<RawRestaurant> rawRestaurants = findAll(pageable);
                    for (RawRestaurant rawRestaurant : rawRestaurants) {
                        if (isNewRestaurant(rawRestaurant)) {
                            save(rawRestaurant);
                            return;
                        }
                        Restaurant restaurant = findByManageNo(rawRestaurant.getManageNo());
                        restaurant.update(rawRestaurant);
                    }
                    printLog(pageable, rawRestaurants);
                } catch (Exception e) {
                    printErrLog(pageable);
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
            i++;
        }
        latch.await();
    }


    private Restaurant findByManageNo(String manageNo) {
        return restaurantRepository.findByManageNo(manageNo)
                .get();
    }

    private Page<RawRestaurant> findAll(Pageable pageable) {
        return rawRestaurantRepository.getAll(pageable);
    }

    private void save(RawRestaurant rawRestaurant) {
        restaurantRepository.save(Restaurant.createFromRaw(rawRestaurant));
    }

    private boolean isNewRestaurant(RawRestaurant rawRestaurant) {
        return !restaurantRepository.existsByManageNo(rawRestaurant.getManageNo());
    }

    private void printErrLog(Pageable pageable) {
        log.error("error occur while batching processing,"
                + " className = {}, page = {} ", this.getClass().getSimpleName(), pageable.getPageNumber());
    }

    private void printLog(Pageable pageable, Page<RawRestaurant> rawRestaurants) {
        log.info("\u23F3 batching processing ~ [now] pageNumber : {}, size : {}, totalSize : {}",
                pageable.getPageNumber(),
                rawRestaurants.getSize(), atomicInteger.getAndSet(rawRestaurants.getSize()));
    }
}
