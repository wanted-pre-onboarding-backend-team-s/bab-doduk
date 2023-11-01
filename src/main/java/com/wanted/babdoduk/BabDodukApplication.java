package com.wanted.babdoduk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BabDodukApplication {

    public static void main(String[] args) {
        SpringApplication.run(BabDodukApplication.class, args);
    }
}
