package com.yolo.launch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


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
	}
}
