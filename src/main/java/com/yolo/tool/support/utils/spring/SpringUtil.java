package com.yolo.tool.support.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * spring 工具类
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware, BeanFactoryPostProcessor {

	private static ConfigurableListableBeanFactory beanFactory;
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtil.applicationContext = context;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		SpringUtil.beanFactory = configurableListableBeanFactory;
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

	public static ApplicationContext getContext() {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext;
	}

	public static void publishEvent(ApplicationEvent event) {
		if (applicationContext == null) {
			return;
		}
		try {
			applicationContext.publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
