

package com.yolo.common.support.retry;

/**
 * 重试接口
 */
public interface IRetry {

	<T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E;

}
