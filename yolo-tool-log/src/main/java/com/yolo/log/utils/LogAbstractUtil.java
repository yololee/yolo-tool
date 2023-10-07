package com.yolo.log.utils;



import com.yolo.common.support.utils.bean.ObjectUtil;
import com.yolo.common.support.utils.date.DateUtil;
import com.yolo.common.support.utils.string.StringPool;
import com.yolo.common.support.utils.web.UrlUtil;
import com.yolo.common.support.utils.web.WebUtil;
import com.yolo.launch.ServerInfo;
import com.yolo.launch.properties.LaunchProperties;
import com.yolo.log.model.LogAbstract;

import javax.servlet.http.HttpServletRequest;

/**
 * Log 相关工具

 */
public class LogAbstractUtil {

	private LogAbstractUtil() {
	}

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
		if (ObjectUtil.isNotEmpty(request)) {
			logAbstract.setRemoteIp(WebUtil.getIP(request));
			logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
			logAbstract.setMethod(request.getMethod());
			logAbstract.setParams(WebUtil.getRequestParamString(request));
//			logAbstract.setCreateBy(SecureUtil.getUserAccount(request));
		}
	}

	/**
	 * 向log中添加补齐其他的信息（eg：blade、server等）
	 *
	 * @param logAbstract     日志基础类
	 * @param launchProperties 配置信息
	 * @param serverInfo      服务信息
	 */
	public static void addOtherInfoToLog(LogAbstract logAbstract, LaunchProperties launchProperties, ServerInfo serverInfo) {
		logAbstract.setServiceId(launchProperties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(launchProperties.getEnv());
		logAbstract.setCreateTime(DateUtil.now());

		//这里判断一下params为null的情况，否则yolo-log服务在解析该字段的时候，可能会报出NPE
		if (logAbstract.getParams() == null) {
			logAbstract.setParams(StringPool.EMPTY);
		}
	}
}
