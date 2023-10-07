package com.yolo.mybatisplus.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yolo.common.support.Kv;
import com.yolo.common.support.utils.Func;
import com.yolo.common.support.utils.bean.BeanUtil;
import com.yolo.common.support.utils.string.StringUtil;

import java.util.Map;

/**
 * 分页工具
 * @author jujueaoye
 */
public class Condition {

	private Condition() {
	}

	/**
	 * 转化成mybatis plus中的Page
	 *
	 * @param pageQuery 查询条件
	 * @return IPage
	 */
	public static <T> IPage<T> getPage(PageQuery pageQuery) {
		Page<T> page = new Page<>(Func.toInt(pageQuery.getCurrent(), 1), Func.toInt(pageQuery.getSize(), 10));
		String[] ascArr = Func.toStrArray(pageQuery.getIsAsc());
		for (String asc : ascArr) {
			page.addOrder(OrderItem.asc(StringUtil.cleanIdentifier(asc)));
		}
		String[] descArr = Func.toStrArray(pageQuery.getOrderByColumn());
		for (String desc : descArr) {
			page.addOrder(OrderItem.desc(StringUtil.cleanIdentifier(desc)));
		}
		return page;
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param entity 实体
	 * @param <T>    类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
		return new QueryWrapper<>(entity);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query 查询条件
	 * @param clazz 实体类
	 * @param <T>   类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
		Kv exclude = Kv.init().set("current", "current").set("size", "size").set("ascs", "ascs").set("descs", "descs");
		return getQueryWrapper(query, exclude, clazz);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query   查询条件
	 * @param exclude 排除的查询条件
	 * @param clazz   实体类
	 * @param <T>     类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Map<String, Object> exclude, Class<T> clazz) {
		exclude.forEach((k, v) -> query.remove(k));
		QueryWrapper<T> qw = new QueryWrapper<>();
		qw.setEntity(BeanUtil.newInstance(clazz));
		SqlKeyword.buildCondition(query, qw);
		return qw;
	}

}
