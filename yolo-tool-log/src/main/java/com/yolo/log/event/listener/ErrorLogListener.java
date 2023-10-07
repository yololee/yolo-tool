package com.yolo.log.event.listener;

import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.launch.ServerInfo;
import com.yolo.launch.properties.LaunchProperties;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.ErrorLogEvent;

import com.yolo.log.mapper.LogApiMapper;
import com.yolo.log.mapper.LogErrorMapper;
import com.yolo.log.model.entity.LogError;
import com.yolo.log.utils.LogAbstractUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {
	private final ServerInfo serverInfo;
	private final LaunchProperties launchProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(@NotNull ErrorLogEvent event) {
		Map<String, Object> source = event.getData();
		LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logError, launchProperties, serverInfo);
		logError.setParams(logError.getParams().replace("&amp;", "&"));
        LogErrorMapper logErrorMapper = SpringUtil.getBean(LogErrorMapper.class);
        logErrorMapper.insert(logError);
	}

}
