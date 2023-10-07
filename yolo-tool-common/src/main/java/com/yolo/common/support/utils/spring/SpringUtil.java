package com.yolo.common.support.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * spring 工具类
 */
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware {

	@Nullable
	private static ApplicationContext context;


	public SpringUtil() {
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtil.context = context;
	}

	public static ApplicationContext getApplicationContext(){
		return context;
	}

	@Nullable
	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		assert context != null;
		return context.getBean(clazz);
	}

	@Nullable
	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		assert context != null;
		return (T) context.getBean(beanName, clazz);
	}

	@Nullable
	public static <T> ObjectProvider<T> getBeanProvider(Class<T> clazz) {
		if (context == null) {
			return null;
		}
		return context.getBeanProvider(clazz);
	}

	@Nullable
	public static <T> ObjectProvider<T> getBeanProvider(ResolvableType resolvableType) {
		if (context == null) {
			return null;
		}
		return context.getBeanProvider(resolvableType);
	}

	@Nullable
	public static <T> T getBean(String beanName) {
		if (context == null) {
			return null;
		}
		return (T) context.getBean(beanName);
	}

	public static void publishEvent(ApplicationEvent event) {
		if (null != context) {
			context.publishEvent(event);
		}

	}

	public static void publishEvent(Object event) {
		if (null != context) {
			context.publishEvent(event);
		}
	}

	/**
	 * 获取aop代理对象
	 *
	 * @return 代理对象
	 */
	public static <T> T getCurrentProxy() {
		return (T) AopContext.currentProxy();
	}
}
