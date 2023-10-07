package com.yolo.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 系统日志事件
 */
@Getter
public class ApiLogEvent extends ApplicationEvent {

	private final transient Map<String, Object> data;

	public ApiLogEvent(Map<String, Object> source) {
		super(source);
		this.data = source;
	}

}
