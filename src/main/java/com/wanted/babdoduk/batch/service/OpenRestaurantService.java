package com.wanted.babdoduk.batch.service;

import com.wanted.babdoduk.batch.RawRestaurant;
import com.wanted.babdoduk.batch.client.OpenRestaurantClient;
import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OpenRestaurantService {

    public static final int THREAD_POOL_SIZE = 64;

    private final RawRestaurantRepository rawRestaurantRepository;
    private final OpenRestaurantClient openRestaurantClient;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public OpenRestaurantService(
            RawRestaurantRepository rawRestaurantRepository, OpenRestaurantClient openRestaurantClient) {
        this.rawRestaurantRepository = rawRestaurantRepository;
        this.openRestaurantClient = openRestaurantClient;
    }

    @Transactional
    public void saveUpdateRawRestaurant(int untilPage, int size) throws InterruptedException {
        final int threadCount = untilPage;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 1; i <= threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    List<ClientRestaurant> clientRestaurants = openRestaurantClient.execute(index, size);
                    log.info("index/total: [{}/{}],  size = [{}]", index, threadCount,
                            atomicInteger.addAndGet(clientRestaurants.size()));
                    for (ClientRestaurant clientRestaurant : clientRestaurants) {
                        updateOrSaveRestaurant(clientRestaurant);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    private void updateOrSaveRestaurant(ClientRestaurant clientRestaurant) {
        Optional<RawRestaurant> byManageNo = rawRestaurantRepository.findByManageNo(clientRestaurant.getManageNo());
        if (byManageNo.isPresent()) {
            RawRestaurant existingRestaurant = byManageNo.get();
            existingRestaurant.update(clientRestaurant);
        } else {
            rawRestaurantRepository.save(RawRestaurant.of(clientRestaurant));
        }
    }
}
