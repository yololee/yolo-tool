package com.yolo.common.config.thread;

import com.yolo.common.properties.AsyncProperties;
import com.yolo.common.support.utils.thread.ThreadUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author jujueaoye*/
@Component
@RequiredArgsConstructor
public class ThreadPoolConfig {

    /**
     * 核心线程数 = cpu 核心数 + 1
     */
    private final int core = Runtime.getRuntime().availableProcessors() + 1;

    private final AsyncProperties asyncProperties;

    @Bean(name = "threadPoolTaskExecutor")
    @ConditionalOnProperty(prefix = "yolo.async", name = "enabled", havingValue = "true")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(core);
        executor.setMaxPoolSize(core * 2);
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncProperties.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(core,
            new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build(),
            new ThreadPoolExecutor.CallerRunsPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadUtil.printException(r, t);
            }
        };
    }
}
