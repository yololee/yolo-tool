package com.yolo.common.support.spel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * ExpressionRootObject
 */
@Getter
@RequiredArgsConstructor
public class YoloExpressionRootObject {
	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;

	private final Method targetMethod;
}
