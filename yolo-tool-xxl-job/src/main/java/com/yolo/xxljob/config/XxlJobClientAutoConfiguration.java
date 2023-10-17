package com.yolo.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.yolo.xxljob.properties.XxlJobProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * xxl-job client config
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = XxlJobProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class XxlJobClientAutoConfiguration {

	private final XxlJobProperties xxlJobProperties;

	@Bean
	public XxlJobSpringExecutor xxlJobExecutor(Environment environment) {
		log.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddress());
		xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAdmin().getAccessToken());

		XxlJobProperties.XxlJobExecutorProps executor = xxlJobProperties.getExecutor();
		String serviceName = getAppName(environment);
		xxlJobSpringExecutor.setAppname(getExecutorName(executor,serviceName));
		xxlJobSpringExecutor.setIp(executor.getIp());
		xxlJobSpringExecutor.setPort(executor.getPort());
		xxlJobSpringExecutor.setLogPath(getLogPath(executor,environment,serviceName));
		xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());
		return xxlJobSpringExecutor;
	}

	private static String getLogPath(XxlJobProperties.XxlJobExecutorProps executor, Environment environment, String serviceName) {
		String logPath = executor.getLogPath();
		if (!StringUtils.hasText(logPath)) {
			logPath = environment.getProperty("LOGGING_PATH", "logs")
					.concat("/").concat(serviceName).concat("/jobs");
		}
		return logPath;
	}

	private static String getAppName(Environment environment) {
		return environment.getProperty("spring.application.name", "");
	}

	private static String getExecutorName(XxlJobProperties.XxlJobExecutorProps executor, String serviceName) {
		String appName = executor.getAppName();
		if (StringUtils.hasText(appName)) {
			return appName;
		}
		return serviceName;
	}

}
