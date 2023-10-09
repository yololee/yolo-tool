package com.yolo.oss.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.yolo.oss.QiniuTemplate;
import com.yolo.oss.props.OssProperties;
import com.yolo.oss.rule.OssRule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Qiniu配置类
 */
@AllArgsConstructor
@AutoConfiguration(after = OssConfiguration.class)
@ConditionalOnClass({Auth.class, UploadManager.class, BucketManager.class})
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "yolo.oss.name", havingValue = "qiniu")
public class QiniuConfiguration {

	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Bean
	@ConditionalOnMissingBean(com.qiniu.storage.Configuration.class)
	public com.qiniu.storage.Configuration qnConfiguration() {
		return new com.qiniu.storage.Configuration(Region.autoRegion());
	}

	@Bean
	@ConditionalOnMissingBean(Auth.class)
	public Auth auth() {
		return Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
	}

	@Bean
	@ConditionalOnBean(com.qiniu.storage.Configuration.class)
	public UploadManager uploadManager(com.qiniu.storage.Configuration cfg) {
		return new UploadManager(cfg);
	}

	@Bean
	@ConditionalOnBean(com.qiniu.storage.Configuration.class)
	public BucketManager bucketManager(com.qiniu.storage.Configuration cfg) {
		return new BucketManager(Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey()), cfg);
	}

	@Bean
	@ConditionalOnBean({Auth.class, UploadManager.class, BucketManager.class})
	@ConditionalOnMissingBean(QiniuTemplate.class)
	public QiniuTemplate qiniuTemplate(Auth auth, UploadManager uploadManager, BucketManager bucketManager) {
		return new QiniuTemplate(auth, uploadManager, bucketManager, ossProperties, ossRule);
	}

}
