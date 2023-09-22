package com.yolo.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置
 */
@Getter
@Setter
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {
	public static final String PREFIX = "yolo.swagger";

	/**
	 * 是否开启 swagger，默认：true
	 */
	private boolean enabled = true;
	/**
	 * 标题，默认：XXX服务
	 */
	private String title;
	/**
	 * 详情，默认：XXX服务
	 */
	private String description;
	/**
	 * 版本号，默认：V1.0
	 */
	private String version = "V1.0";
	/**
	 * 组织名
	 */
	private String contactUser;
	/**
	 * 组织url
	 */
	private String contactUrl;
	/**
	 * 组织邮箱
	 */
	private String contactEmail;
}
