package com.yolo.redis.exception;

import com.yolo.common.support.utils.string.StringUtil;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 限流异常
 */
@Getter
public class RateLimiterException extends RuntimeException {
	private final String key;
	private final long max;
	private final long ttl;
	private final TimeUnit timeUnit;

	public RateLimiterException(String key, long max, long ttl, TimeUnit timeUnit) {
		super(StringUtil.format("您的访问次数已超限：{}，速率：{}/{}",key,max,timeUnit.toSeconds(ttl)));
		this.key = key;
		this.max = max;
		this.ttl = ttl;
		this.timeUnit = timeUnit;
	}
}
