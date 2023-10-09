package com.yolo.oss.config;

import com.yolo.oss.MinioTemplate;
import com.yolo.oss.props.OssProperties;
import com.yolo.oss.rule.OssRule;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Minio配置类
 */
@AllArgsConstructor
@AutoConfiguration(after = OssConfiguration.class)
@ConditionalOnClass({MinioClient.class})
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "yolo.oss.name", havingValue = "minio")
public class MinioConfiguration {

	private final OssProperties ossProperties;
	private final OssRule ossRule;


	@Bean
	@SneakyThrows
	@ConditionalOnMissingBean(MinioClient.class)
	public MinioClient minioClient() {
		return MinioClient.builder()
			.endpoint(ossProperties.getEndpoint())
			.credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
			.build();
	}

	@Bean
	@ConditionalOnBean({MinioClient.class})
	@ConditionalOnMissingBean(MinioTemplate.class)
	public MinioTemplate minioTemplate(MinioClient minioClient) {
		return new MinioTemplate(minioClient, ossRule, ossProperties);
	}

}
