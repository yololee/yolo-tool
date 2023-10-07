package com.yolo.log.event.listener;


import com.yolo.common.properties.LaunchProperties;
import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.launch.ServerInfo;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.UsualLogEvent;
import com.yolo.log.mapper.LogUsualMapper;
import com.yolo.log.model.entity.LogUsual;
import com.yolo.log.utils.LogAbstractUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

	private final ServerInfo serverInfo;
	private final LaunchProperties launchProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = event.getData();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logUsual, launchProperties, serverInfo);
		logUsual.setParams(logUsual.getParams().replace("&amp;", "&"));
        LogUsualMapper logUsualMapper = SpringUtil.getBean(LogUsualMapper.class);
        logUsualMapper.insert(logUsual);
	}

}
