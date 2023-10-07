package com.yolo.log.publisher;



import com.yolo.common.constant.SystemConstant;
import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.common.support.utils.web.WebUtil;
import com.yolo.log.annotation.ApiLog;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.ApiLogEvent;
import com.yolo.log.model.entity.LogApi;
import com.yolo.log.utils.LogAbstractUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 */
@RequiredArgsConstructor
public class ApiLogPublisher {

	private ApplicationEventPublisher eventPublisher;

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(SystemConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);

		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
