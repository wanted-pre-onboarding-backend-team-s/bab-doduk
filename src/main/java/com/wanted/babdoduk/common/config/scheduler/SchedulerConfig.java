package com.wanted.babdoduk.common.config.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@Configuration
class SchedulerConfig {

    private final static int SCHEDULER_POOL_SIZE = 5;
    private final static String SCHEDULER_THREAD_NAME_PREFIX = "sc-thread-";

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(SCHEDULER_POOL_SIZE);
        scheduler.setThreadNamePrefix(SCHEDULER_THREAD_NAME_PREFIX);
        return scheduler;
    }

}
