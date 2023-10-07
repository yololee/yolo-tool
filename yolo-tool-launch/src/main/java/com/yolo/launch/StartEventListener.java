package com.yolo.launch;

import com.yolo.launch.log.LogPrintStream;
import com.yolo.common.support.utils.web.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;


/**
 * 项目启动事件通知
 */
@Slf4j
@Component
public class StartEventListener {

	@Async
	@Order
	@EventListener(WebServerInitializedEvent.class)
	public void afterStart(WebServerInitializedEvent event) {
		Environment environment = event.getApplicationContext().getEnvironment();
		String name = environment.getProperty("spring.application.name");
		int localPort = event.getWebServer().getPort();
		String profile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
		if (StringUtils.hasText(name)){
			log.info("---[{}]---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", name.toUpperCase(), localPort, profile);
		}else {
			log.info("---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", localPort, profile);
		}

		// 如果有 swagger，打印开发阶段的 swagger ui 地址
		if (hasOpenApi()) {
			System.out.printf("http://localhost:%s/doc.html%n", localPort);
			System.out.printf("http://localhost:%s/swagger-ui/index.html%n", localPort);
		} else {
			System.out.printf("http://localhost:%s%n", localPort);
		}
		// linux 上将全部的 System.err 和 System.out 替换为log
		if (SystemUtil.isLinux()) {
			System.setOut(LogPrintStream.log(false));
			// 去除 error 的转换，因为 error 会打印成很 N 条
			// System.setErr(LogPrintStream.log(true));
		}

	}

	private static boolean hasOpenApi() {
		return Stream.of("springfox.documentation.spring.web.plugins.Docket", "io.swagger.v3.oas.models.OpenAPI")
				.anyMatch(clazz -> ClassUtils.isPresent(clazz, null));
	}
}
