package com.yolo.xss.core;

import com.yolo.common.properties.XssProperties;
import com.yolo.common.support.utils.bean.ClassUtil;
import com.yolo.xss.anno.XssCleanIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * xss 处理拦截器
 */
@RequiredArgsConstructor
public class XssCleanInterceptor implements AsyncHandlerInterceptor {
	private final XssProperties xssProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 2. 没有开启
		if (!xssProperties.isEnabled()) {
			return true;
		}
		// 3. 处理 XssIgnore 注解
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		XssCleanIgnore xssCleanIgnore = ClassUtil.getAnnotation(handlerMethod, XssCleanIgnore.class);
		if (xssCleanIgnore != null) {
			XssHolder.setIgnore(xssCleanIgnore);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		XssHolder.remove();
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		XssHolder.remove();
	}
}
