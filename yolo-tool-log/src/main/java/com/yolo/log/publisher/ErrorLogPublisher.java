
package com.yolo.log.publisher;

import com.yolo.common.support.utils.Func;
import com.yolo.common.support.utils.exception.ExceptionUtil;
import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.common.support.utils.web.WebUtil;
import com.yolo.log.constant.EventConstant;
import com.yolo.log.event.ErrorLogEvent;
import com.yolo.log.model.entity.LogError;
import com.yolo.log.utils.LogAbstractUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送

 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(ExceptionUtil.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}
