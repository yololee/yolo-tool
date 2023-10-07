package com.yolo.log.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 错误日志事件
 */
@Getter
public class ErrorLogEvent extends ApplicationEvent {

	private final transient Map<String, Object> data;

	public ErrorLogEvent(Map<String, Object> source) {
		super(source);
		this.data = source;
	}

}
