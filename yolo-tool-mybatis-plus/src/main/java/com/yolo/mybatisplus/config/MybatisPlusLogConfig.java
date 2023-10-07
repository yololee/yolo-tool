package com.yolo.mybatisplus.config;


import com.yolo.mybatisplus.plus.SqlLogInterceptor;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * mybatis-plus 自定义日志打印
 * @author jujueaoye
 */
@Component
public class MybatisPlusLogConfig {


    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    @ConditionalOnProperty(value = "yolo.mybatis.sql-log", havingValue = "true")
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

}
