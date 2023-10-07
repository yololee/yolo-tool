package com.yolo.common.support.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * spring 工具类
 */
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware, BeanFactoryPostProcessor {

	private static ConfigurableListableBeanFactory beanFactory;
	private static ApplicationContext applicationContext;


	public SpringUtil() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtil.beanFactory = beanFactory;
	}

	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		return applicationContext.getBean(clazz);
	}

	public static <T> T getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		return (T) applicationContext.getBean(beanId);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		return (T) applicationContext.getBean(beanName, clazz);
	}

	public static void publishEvent(ApplicationEvent event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}

	}

	public static void publishEvent(Object event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}

	}
}
