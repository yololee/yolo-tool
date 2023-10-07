package com.yolo.xss.anno;

import java.lang.annotation.*;

/**
 * 忽略 xss
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssCleanIgnore {

	/**
	 * 支持指定忽略的字段
	 *
	 * @return 字段数组
	 */
	String[] value() default {};

}
