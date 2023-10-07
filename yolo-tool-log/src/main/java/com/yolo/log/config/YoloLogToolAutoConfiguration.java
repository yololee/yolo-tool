package com.yolo.log.config;

import com.yolo.launch.ServerInfo;
import com.yolo.launch.properties.LaunchProperties;
import com.yolo.log.aspect.ApiLogAspect;
import com.yolo.log.event.listener.ApiLogListener;
import com.yolo.log.event.listener.ErrorLogListener;
import com.yolo.log.event.listener.UsualLogListener;
import com.yolo.log.logger.YoloLogger;
import com.yolo.log.properties.YoloLogProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 日志工具自动配置
 *
 * @author jujueaoye
 */
@Component
@RequiredArgsConstructor
@ConditionalOnWebApplication
public class YoloLogToolAutoConfiguration {
    private final ServerInfo serverInfo;
    private final LaunchProperties launchProperties;

    @Bean
    @ConditionalOnProperty(value = YoloLogProperties.PREFIX + "api.enabled", havingValue = "true", matchIfMissing = true)
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @Bean
    @ConditionalOnProperty(value = YoloLogProperties.PREFIX + "api.enabled", havingValue = "true", matchIfMissing = true)
    public ApiLogListener apiLogListener() {
        return new ApiLogListener(serverInfo, launchProperties);
    }

    @Bean
    @ConditionalOnProperty(value = YoloLogProperties.PREFIX + "error.enabled", havingValue = "true", matchIfMissing = true)
    public ErrorLogListener errorEventListener() {
        return new ErrorLogListener(serverInfo, launchProperties);
    }

    @Bean
    @ConditionalOnProperty(value = YoloLogProperties.PREFIX + "usual.enabled", havingValue = "true", matchIfMissing = true)
    public UsualLogListener bladeEventListener() {
        return new UsualLogListener(serverInfo, launchProperties);
    }

    @Bean
    @ConditionalOnProperty(value = YoloLogProperties.PREFIX + "usual.enabled", havingValue = "true", matchIfMissing = true)
    public YoloLogger bladeLogger() {
        return new YoloLogger();
    }

}
