package com.yolo.xss.core;

import com.yolo.xss.anno.XssCleanIgnore;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 利用 ThreadLocal 缓存线程间的数据
 */
@UtilityClass
public class XssHolder {
	private static final ThreadLocal<XssCleanIgnore> TL = new ThreadLocal<>();

	/**
	 * 是否开启
	 *
	 * @return boolean
	 */
	public static boolean isEnabled() {
		return Objects.isNull(TL.get());
	}

	/**
	 * 判断是否被忽略
	 *
	 * @return XssCleanIgnore
	 */
	public static boolean isIgnore(String name) {
		XssCleanIgnore cleanIgnore = TL.get();
		if (cleanIgnore == null) {
			return false;
		}
		String[] ignoreArray = cleanIgnore.value();
		// 1. 如果没有设置忽略的字段
		if (ignoreArray.length == 0) {
			return true;
		}
		// 2. 指定忽略的属性
		return ObjectUtils.containsElement(ignoreArray, name);
	}

	/**
	 * 标记为开启
	 */
	static void setIgnore(XssCleanIgnore xssCleanIgnore) {
		TL.set(xssCleanIgnore);
	}

	/**
	 * 关闭 xss 清理
	 */
	public static void remove() {
		TL.remove();
	}

}
