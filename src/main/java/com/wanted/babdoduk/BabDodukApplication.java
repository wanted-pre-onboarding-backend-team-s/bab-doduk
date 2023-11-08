package com.wanted.babdoduk;

import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.batch.service.OpenRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
@EnableAsync
public class BabDodukApplication implements CommandLineRunner {

    public static final int INIT_SIZE = 0;

    @Autowired
    private OpenRestaurantService openRestaurantService;

    @Autowired
    private RawRestaurantRepository rawRestaurantRepository;

    public static void main(String[] args) {
        SpringApplication.run(BabDodukApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (rawRestaurantRepository.count() == INIT_SIZE) {
            System.out.println("API -> RAW_DATA 저장 및 업데이트 배치 작업 시작합니다.");
            openRestaurantService.initSave();
            System.out.println("API -> RAW_DATA 저장 및 업데이트 배치 작업이 종료되었습니다.");
        }
    }
}
