package com.yolo.xss.core;


import com.yolo.common.support.utils.exception.ExceptionUtil;
import com.yolo.xss.exception.FromXssException;
import com.yolo.xss.exception.JacksonXssException;

/**
 * xss 数据处理类型
 */
public enum XssType {

	/**
	 * 表单
	 */
	FORM() {
		@Override
		public RuntimeException getXssException(String name, String input, String message) {
			return new FromXssException(input, message);
		}
	},

	/**
	 * body json
	 */
	JACKSON() {
		@Override
		public RuntimeException getXssException(String name, String input, String message) {
			return ExceptionUtil.unchecked(new JacksonXssException(name, input, message));
		}
	};

	/**
	 * 获取 xss 异常
	 *
	 * @param name    属性名
	 * @param input   input
	 * @param message message
	 * @return XssException
	 */
	public abstract RuntimeException getXssException(String name, String input, String message);

}
