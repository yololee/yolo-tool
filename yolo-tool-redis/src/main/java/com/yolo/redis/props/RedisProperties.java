package com.yolo.redis.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis 配置

 */
@Getter
@Setter
@Component
@ConfigurationProperties(RedisProperties.PREFIX)
public class RedisProperties {
	public static final String PREFIX = "yolo.redis";

	/**
	 * redis key 前缀
	 */
	private String keyPrefix;
	/**
	 * 序列化方式
	 */
	private SerializerType serializerType = SerializerType.JSON;
	/**
	 * key 过期事件
	 */
	private KeyExpiredEvent keyExpiredEvent = new KeyExpiredEvent();
	/**
	 * 限流配置
	 */
	private RateLimiter rateLimiter = new RateLimiter();

	/**
	 * 重复提交配置
	 */
	private RepeatSubmit repeatSubmit = new RepeatSubmit();

	/**
	 * 序列化方式
	 */
	public enum SerializerType {
		/**
		 * json 序列化
		 */
		JSON,
		/**
		 * jdk 序列化
		 */
		JDK
	}

	@Getter
	@Setter
	public static class KeyExpiredEvent {
		/**
		 * 是否开启 redis key 失效事件.
		 */
		boolean enable = false;
	}

	@Getter
	@Setter
	public static class RateLimiter {
		/**
		 * 是否开启 RateLimiter
		 */
		boolean enable = false;
	}

	@Getter
	@Setter
	public static class RepeatSubmit {
		/**
		 * 是否开启 RepeatSubmit
		 */
		boolean enable = false;
	}

}
