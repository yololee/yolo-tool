package com.yolo.common.config;

import com.yolo.common.properties.RequestProperties;
import com.yolo.common.properties.XssProperties;
import com.yolo.common.support.request.YoloRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.DispatcherType;

/**
 * 过滤器配置类
 */
@Component
@AllArgsConstructor
public class RequestConfiguration {

	private final RequestProperties requestProperties;
	private final XssProperties xssProperties;

	/**
	 * 全局过滤器
	 *
	 * @return 自定义过滤器
	 */
	@Bean
	public FilterRegistrationBean<YoloRequestFilter> bladeFilterRegistration() {
		FilterRegistrationBean<YoloRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new YoloRequestFilter(requestProperties, xssProperties));
		registration.addUrlPatterns("/*");
		registration.setName("yoloRequestFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
