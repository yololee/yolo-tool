package com.yolo.redis.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.yolo.common.support.utils.jackson.JsonUtil;
import com.yolo.redis.props.RedisProperties;
import com.yolo.redis.resolver.DefaultRedisKeyResolver;
import com.yolo.redis.resolver.RedisKeyResolver;
import com.yolo.redis.resolver.RedisKeyResolverSerializer;
import com.yolo.redis.utils.RedisUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate  配置
 * @author jujueaoye
 */
//@EnableCaching
@AutoConfiguration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {

	/**
	 * 默认的 redis key 处理
	 * @param properties MicaRedisProperties
	 * @return RedisKeyResolver
	 */
	@Bean
	@ConditionalOnMissingBean(RedisKeyResolver.class)
	public RedisKeyResolver redisKeyResolver(RedisProperties properties) {
		return new DefaultRedisKeyResolver(properties);
	}

	/**
	 * value 值 序列化
	 *
	 * @return RedisSerializer
	 */
	@Bean
	@ConditionalOnMissingBean(RedisSerializer.class)
	public RedisSerializer<Object> redisSerializer(RedisProperties properties) {
		RedisProperties.SerializerType serializerType = properties.getSerializerType();
		if (RedisProperties.SerializerType.JDK == serializerType) {
			ClassLoader classLoader = this.getClass().getClassLoader();
			return new JdkSerializationRedisSerializer(classLoader);
		}
		// jackson findAndRegisterModules，use copy
		ObjectMapper objectMapper = JsonUtil.getInstance();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// findAndRegisterModules
		objectMapper.findAndRegisterModules();
		// class type info to json
		GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
		objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL, As.PROPERTY);
		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
														   RedisKeyResolver redisKeyResolver,
														   RedisSerializer<Object> redisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// key 序列化
		RedisKeyResolverSerializer keySerializer = new RedisKeyResolverSerializer(RedisSerializer.string(), redisKeyResolver);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		// value 序列化
		redisTemplate.setValueSerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(redisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(ValueOperations.class)
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> micaRedisTemplate) {
		return micaRedisTemplate.opsForValue();
	}

	@Bean(name = "redisUtil")
	@ConditionalOnBean(RedisTemplate.class)
	public RedisUtil redisUtils(RedisTemplate<String, Object> redisTemplate) {
		return new RedisUtil(redisTemplate);
	}

}
