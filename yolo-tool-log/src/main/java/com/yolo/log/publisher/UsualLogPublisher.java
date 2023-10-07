package com.yolo.log.publisher;


import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.common.support.utils.web.WebUtil;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.UsualLogEvent;
import com.yolo.log.model.entity.LogUsual;
import com.yolo.log.utils.LogAbstractUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志信息事件发送
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);

		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}
