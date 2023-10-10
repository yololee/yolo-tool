package com.yolo.redis.cache;

import com.yolo.common.support.utils.string.StringPool;
import com.yolo.common.support.utils.string.StringUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * cache key
 */
public interface ICacheKey {

	/**
	 * 获取前缀
	 *
	 * @return key 前缀
	 */
	String getPrefix();

	/**
	 * 超时时间
	 *
	 * @return 超时时间
	 */
	@Nullable
	default Duration getExpire() {
		return null;
	}

	/**
	 * 组装 cache key
	 *
	 * @param suffix 参数
	 * @return cache key
	 */
	default String getKeyStr(Object... suffix) {
		String prefix = this.getPrefix();
		// 拼接参数
		if (ObjectUtils.isEmpty(suffix)) {
			return prefix;
		}
		return prefix.concat(StringUtil.join(suffix, StringPool.COLON));
	}

	/**
	 * 组装 cache key
	 *
	 * @param suffix 参数
	 * @return cache key
	 */
	default CacheKey getKey(Object... suffix) {
		String key = this.getKeyStr(suffix);
		Duration expire = this.getExpire();
		return expire == null ? new CacheKey(key) : new CacheKey(key, expire);
	}

}
