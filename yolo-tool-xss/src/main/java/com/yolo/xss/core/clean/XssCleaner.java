package com.yolo.xss.core.clean;

import com.yolo.xss.core.XssType;
import com.yolo.xss.utils.XssUtil;
import org.jsoup.Jsoup;

/**
 * xss 清理器
 */
public interface XssCleaner {

	/**
	 * 清理 html
	 *
	 * @param value 属性值
	 * @param type  XssType
	 * @return 清理后的数据
	 */
	default String clean(String value, XssType type) {
		return clean(null, value, type);
	}

	/**
	 * 清理 html
	 *
	 * @param name  属性名
	 * @param value 属性值
	 * @param type  XssType
	 * @return 清理后的数据
	 */
	String clean(String name, String value, XssType type);

	/**
	 * 判断输入是否安全
	 *
	 * @param html html
	 * @return 是否安全
	 */
	default boolean isValid(String html) {
		return Jsoup.isValid(html, XssUtil.WHITE_LIST);
	}

}
