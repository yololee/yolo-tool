package com.yolo.redis.config;

import com.yolo.redis.aspect.RepeatSubmitAspect;
import com.yolo.redis.utils.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 基于 redis 的重复提交自动配置
 */
@Component
@ConditionalOnProperty(value = "yolo.redis.repeat-submit.enable")
public class RepeatSubmitAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public RepeatSubmitAspect redisRepeatSubmitAspect(RedisUtil redisUtil) {
        return new RepeatSubmitAspect(redisUtil);
    }
}
