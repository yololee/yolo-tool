package com.yolo.tool.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yolo")
public class YoloConfigProperties {

    /**
     * swagger 配置信息
     */
    @NestedConfigurationProperty
    private Swagger swagger = new Swagger();


    @Data
    public static class Swagger{
        /**
         * 是否启动 swagger 配置
         */
        private Boolean enabled = Boolean.FALSE;
    }

}
