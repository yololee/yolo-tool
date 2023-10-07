package com.yolo.mybatisplus.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(MybatisPlusLogProperties.PREFIX)
@EnableConfigurationProperties(MybatisPlusLogProperties.class)// 如果是自动配置类  请通过@EnableConfigurationProperties启用
public class MybatisPlusLogProperties {

    public static final String PREFIX = "yolo.mybatis.sql-log";

    /**
     * 是否开启 log ，默认：true
     */
    private boolean enabled = true;
}
