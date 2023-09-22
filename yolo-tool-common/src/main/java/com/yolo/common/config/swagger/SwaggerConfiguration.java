package com.yolo.common.config.swagger;


import com.yolo.common.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Optional;

/**
 * Swagger2配置
 */
@Component
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerConfiguration {

	@Bean
	public Docket docket(Environment environment, SwaggerProperties properties) {
		// 1. 组名为应用名
		String appName = environment.getProperty("spring.application.name");
		return  new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo(appName, properties)).select()
			.apis(SwaggerConfiguration::isSwaggerController)
			.paths(PathSelectors.any())
			.build();
	}

	private static boolean isSwaggerController(RequestHandler requestHandler) {
		Optional<Api> optional = requestHandler.findControllerAnnotation(Api.class);
		if (optional.isPresent()) {
			return true;
		}
		return requestHandler.findControllerAnnotation(Tag.class).isPresent();
	}

	private static ApiInfo apiInfo(@Nullable String appName, SwaggerProperties properties) {
		String defaultName = (appName == null ? "" : appName) + "服务";
		String title = Optional.ofNullable(properties.getTitle())
			.orElse(defaultName);
		String description = Optional.ofNullable(properties.getDescription())
			.orElse(defaultName);
		return new ApiInfoBuilder()
			.title(title)
			.description(description)
			.version(properties.getVersion())
			.contact(new Contact(properties.getContactUser(), properties.getContactUrl(), properties.getContactEmail()))
			.build();
	}

}
