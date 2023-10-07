package com.yolo.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 异步配置
 *
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties(YoloLogProperties.PREFIX)
@Component
public class YoloLogProperties {
	/**
	 * 前缀
	 */
	public static final String PREFIX = "yolo.log";

	/**
	 * 是否开启 api 日志
	 */
	private Boolean api = Boolean.TRUE;
	/**
	 * 是否开启 error 日志
	 */
	private Boolean error = Boolean.TRUE;
	/**
	 * 是否开启 usual 日志
	 */
	private Boolean usual = Boolean.TRUE;
}
