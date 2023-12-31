package com.yolo.xss.core.clean;

import com.yolo.common.properties.XssProperties;
import com.yolo.xss.core.XssHolder;
import com.yolo.xss.core.XssType;
import com.yolo.xss.utils.XssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.auto.annotation.AutoIgnore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

/**
 * 表单 xss 处理
 */
@AutoIgnore
@ControllerAdvice
@ConditionalOnProperty(
	prefix = XssProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true
)
@RequiredArgsConstructor
public class FormXssClean {
	private final XssProperties properties;
	private final XssCleaner xssCleaner;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 处理前端传来的表单字符串
		binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssCleaner, properties));
	}

	@Slf4j
	@RequiredArgsConstructor
	public static class StringPropertiesEditor extends PropertyEditorSupport {
		private final XssCleaner xssCleaner;
		private final XssProperties properties;

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (text == null) {
				setValue(null);
			} else if (XssHolder.isEnabled()) {
				String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()), XssType.FORM);
				setValue(value);
				log.debug("Request parameter value:{} cleaned up by yolo, current value is:{}.", text, value);
			} else {
				setValue(XssUtil.trim(text, properties.isTrimText()));
			}
		}
	}

}
