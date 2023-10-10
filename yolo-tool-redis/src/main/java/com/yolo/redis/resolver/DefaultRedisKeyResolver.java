package com.yolo.redis.resolver;

import cn.hutool.core.text.CharPool;
import com.yolo.common.support.utils.string.StringUtil;
import com.yolo.redis.props.RedisProperties;
import lombok.RequiredArgsConstructor;

/**
 * 默认的 key 处理，添加配置的 key 前缀
 */
@RequiredArgsConstructor
public class DefaultRedisKeyResolver implements RedisKeyResolver {
	private final RedisProperties properties;

	@Override
	public String resolve(String key) {
		String keyPrefix = properties.getKeyPrefix();
		return StringUtil.isBlank(keyPrefix) ? key : keyPrefix + CharPool.COLON + key;
	}

}
