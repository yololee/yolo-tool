package com.yolo.log.event.listener;

import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.launch.ServerInfo;
import com.yolo.launch.properties.LaunchProperties;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.ApiLogEvent;
import com.yolo.log.mapper.LogApiMapper;
import com.yolo.log.model.entity.LogApi;
import com.yolo.log.utils.LogAbstractUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;


/**
 * 异步监听日志事件
 */
@Slf4j
@RequiredArgsConstructor
public class ApiLogListener {

	private final ServerInfo serverInfo;
	private final LaunchProperties launchProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(@NotNull ApiLogEvent event) {
		Map<String, Object> source = event.getData();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logApi, launchProperties, serverInfo);
		logApi.setParams(logApi.getParams().replace("&amp;", "&"));
		LogApiMapper logApiMapper = SpringUtil.getBean(LogApiMapper.class);
		logApiMapper.insert(logApi);
	}

}
