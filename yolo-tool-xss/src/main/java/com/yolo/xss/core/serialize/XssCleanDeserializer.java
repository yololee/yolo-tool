package com.yolo.xss.core.serialize;

import com.yolo.common.properties.XssProperties;
import com.yolo.common.support.utils.spring.SpringUtil;
import com.yolo.xss.core.clean.XssCleaner;
import com.yolo.xss.core.XssType;
import com.yolo.xss.utils.XssUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * jackson xss 处理
 */
@Slf4j
public class XssCleanDeserializer extends XssCleanDeserializerBase {

	@Override
	public String clean(String name, String text) throws IOException {
		if (text == null) {
			return null;
		}
		// 读取 xss 配置
		XssProperties properties = SpringUtil.getBean(XssProperties.class);
		if (properties == null) {
			return text;
		}
		// 读取 XssCleaner bean
		XssCleaner xssCleaner =  SpringUtil.getBean(XssCleaner.class);
		if (xssCleaner == null) {
			return XssUtil.trim(text, properties.isTrimText());
		}
		String value = xssCleaner.clean(name, XssUtil.trim(text, properties.isTrimText()), XssType.JACKSON);
		log.debug("Json property name:{} value:{} cleaned up by mica-xss, current value is:{}.", name, text, value);
		return value;
	}

}
