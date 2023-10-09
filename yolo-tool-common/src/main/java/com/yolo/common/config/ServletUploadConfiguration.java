package com.yolo.common.config;

import com.yolo.common.constant.SystemConstant;
import com.yolo.common.properties.UploadProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件上传配置
 */
@Component
@ConditionalOnProperty(prefix = UploadProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletUploadConfiguration implements WebMvcConfigurer {

	/**
	 * Spring Boot 访问静态资源的位置(优先级按以下顺序)
	 * classpath默认就是resources,所以classpath:/static/ 就是resources/static/
	 * classpath:/META‐INF/resources/
	 * classpath:/resources/
	 * classpath:/static/
	 * classpath:/public/
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//文件上传
		registry.addResourceHandler(SystemConstant.RESOURCE_PREFIX)
				.addResourceLocations("file:" + UploadProperties.getUploadPath() + "/");
		//文件导入
		registry.addResourceHandler(SystemConstant.RESOURCE_PREFIX)
				.addResourceLocations("file:" + UploadProperties.getImportPath() + "/");
	}

}
