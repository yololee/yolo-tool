

package com.yolo.common.support.function;

import java.io.Serializable;

/**
 * 受检的 Predicate
 */
@FunctionalInterface
public interface CheckedPredicate<T> extends Serializable {

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param t the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 * @throws Throwable CheckedException
	 */
	boolean test(T t) throws Throwable;

}
