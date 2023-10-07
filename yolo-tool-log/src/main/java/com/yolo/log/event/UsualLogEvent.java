package com.yolo.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 系统日志事件
 */
@Getter
public class UsualLogEvent extends ApplicationEvent {

	private final transient Map<String, Object> data;

	public UsualLogEvent(Map<String, Object> source) {
		super(source);
		this.data = source;
	}

}
