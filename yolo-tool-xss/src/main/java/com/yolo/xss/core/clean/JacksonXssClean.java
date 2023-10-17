package com.yolo.xss.core.clean;

import com.yolo.common.properties.XssProperties;
import com.yolo.xss.core.XssHolder;
import com.yolo.xss.core.XssType;
import com.yolo.xss.core.serialize.XssCleanDeserializerBase;
import com.yolo.xss.utils.XssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * jackson xss 处理
 */
@Slf4j
@RequiredArgsConstructor
public class JacksonXssClean extends XssCleanDeserializerBase {
	private final XssProperties properties;
	private final XssCleaner xssCleaner;

	@Override
	public String clean(String name, String text) {
		if (text == null) {
			return null;
		}
		// 判断是否忽略
		if (XssHolder.isIgnore(name)) {
			return XssUtil.trim(text, properties.isTrimText());
		}
		String value = xssCleaner.clean(name, XssUtil.trim(text, properties.isTrimText()), XssType.JACKSON);
		log.debug("Json property name:{} value:{} cleaned up by yolo, current value is:{}.", name, text, value);
		return value;
	}

}
