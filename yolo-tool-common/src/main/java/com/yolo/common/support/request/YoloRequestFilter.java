package com.yolo.common.support.request;

import com.yolo.common.properties.RequestProperties;
import com.yolo.common.properties.XssProperties;
import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request全局过滤
 */
@AllArgsConstructor
public class YoloRequestFilter implements Filter {

	private final RequestProperties requestProperties;
	private final XssProperties xssProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		// 跳过 Request 包装
		if (Boolean.TRUE.equals(!requestProperties.getEnabled()) || isRequestSkip(path)) {
			chain.doFilter(request, response);
		} else if (!xssProperties.isEnabled() || isXssSkip(path)) {
			// 默认 Request 包装
			YoloHttpServletRequestWrapper bladeRequest = new YoloHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(bladeRequest, response);
		}else {
			chain.doFilter(request, response);
		}

	}

	private boolean isRequestSkip(String path) {
		return requestProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	private boolean isXssSkip(String path) {
		return xssProperties.getPathExcludePatterns().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	@Override
	public void destroy() {

	}

}
