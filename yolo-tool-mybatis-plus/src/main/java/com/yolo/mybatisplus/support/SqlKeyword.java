package com.yolo.mybatisplus.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yolo.common.support.utils.Func;
import com.yolo.common.support.utils.date.DateUtil;
import com.yolo.common.support.utils.string.StringPool;
import com.yolo.common.support.utils.string.StringUtil;

import java.util.Map;

/**
 * 定义常用的 sql关键字
 */
public class SqlKeyword {

	private SqlKeyword() {
	}

	private static final String SQL_REGEX = "'|%|--|insert|delete|select|sleep|count|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql";

	/**
	 *	等于
	 */
	public static final String EQUAL = "_equal";
	/**
	 * 不等于
	 */
	public static final String NOT_EQUAL = "_notequal";
	/**
	 * 模糊查询
	 */
	public static final String LIKE = "_like";
	/**
	 * 左模糊查询
	 */
	public static final String LIKE_LEFT = "_likeleft";
	/**
	 * 右模糊查询
	 */
	public static final String LIKE_RIGHT = "_likeright";
	/**
	 * 不是模糊查询
	 */
	public static final String NOT_LIKE = "_notlike";
	/**
	 * 大于等于
	 */
	public static final String GE = "_ge";
	/**
	 * 小于等于
	 */
	public static final String LE = "_le";
	/**
	 * 大于
	 */
	public static final String GT = "_gt";
	/**
	 * 小于
	 */
	public static final String LT = "_lt";
	/**
	 *
	 */
	private static final String DATE_GE = "_datege";
	public static final String DATE_GT = "_dategt";
	public static final String DATE_EQUAL = "_dateequal";
	public static final String DATE_LT = "_datelt";
	public static final String DATE_LE = "_datele";
	public static final String IS_NULL = "_null";
	public static final String NOT_NULL = "_notnull";
	public static final String IGNORE = "_ignore";

	/**
	 * 条件构造器
	 *
	 * @param query 查询字段
	 * @param qw    查询包装类
	 */
	public static void buildCondition(Map<String, Object> query, QueryWrapper<?> qw) {
		if (Func.isEmpty(query)) {
			return;
		}
		query.forEach((k, v) -> {
			if (Func.hasEmpty(k, v) || k.endsWith(IGNORE)) {
				return;
			}
			// 过滤sql注入关键词
			k = filter(k);
			if (k.endsWith(EQUAL)) {
				qw.eq(getColumn(k, EQUAL), v);
			} else if (k.endsWith(NOT_EQUAL)) {
				qw.ne(getColumn(k, NOT_EQUAL), v);
			} else if (k.endsWith(LIKE_LEFT)) {
				qw.likeLeft(getColumn(k, LIKE_LEFT), v);
			} else if (k.endsWith(LIKE_RIGHT)) {
				qw.likeRight(getColumn(k, LIKE_RIGHT), v);
			} else if (k.endsWith(NOT_LIKE)) {
				qw.notLike(getColumn(k, NOT_LIKE), v);
			} else if (k.endsWith(GE)) {
				qw.ge(getColumn(k, GE), v);
			} else if (k.endsWith(LE)) {
				qw.le(getColumn(k, LE), v);
			} else if (k.endsWith(GT)) {
				qw.gt(getColumn(k, GT), v);
			} else if (k.endsWith(LT)) {
				qw.lt(getColumn(k, LT), v);
			} else if (k.endsWith(DATE_GE)) {
				qw.ge(getColumn(k, DATE_GE), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_GT)) {
				qw.gt(getColumn(k, DATE_GT), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_EQUAL)) {
				qw.eq(getColumn(k, DATE_EQUAL), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_LE)) {
				qw.le(getColumn(k, DATE_LE), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_LT)) {
				qw.lt(getColumn(k, DATE_LT), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(IS_NULL)) {
				qw.isNull(getColumn(k, IS_NULL));
			} else if (k.endsWith(NOT_NULL)) {
				qw.isNotNull(getColumn(k, NOT_NULL));
			} else {
				qw.like(getColumn(k, LIKE), v);
			}
		});
	}

	/**
	 * 获取数据库字段
	 *
	 * @param column  字段名
	 * @param keyword 关键字
	 * @return String
	 */
	private static String getColumn(String column, String keyword) {
		return StringUtil.humpToUnderline(StringUtil.removeSuffix(column, keyword));
	}

	/**
	 * 把SQL关键字替换为空字符串
	 *
	 * @param param 关键字
	 * @return string
	 */
	public static String filter(String param) {
		if (param == null) {
			return null;
		}
		return param.replaceAll("(?i)" + SQL_REGEX, StringPool.EMPTY);
	}
}
