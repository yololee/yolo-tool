package com.yolo.tool.support.utils.collection;

import com.yolo.tool.support.utils.bean.ObjectUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 集合工具类
 */
public class CollectionUtil extends CollectionUtils {

	/**
	 * Check whether the given Array contains the given element.
	 *
	 * @param array   the Array to check
	 * @param element the element to look for
	 * @param <T>     The generic tag
	 * @return {@code true} if found, {@code false} else
	 */
	public static <T> boolean contains(@Nullable T[] array, final T element) {
		if (array == null) {
			return false;
		}
		return Arrays.stream(array).anyMatch(x -> ObjectUtil.nullSafeEquals(x, element));
	}

	/**
	 * 创建一个空的list 或者根据传入的值创建list
	 * @param values 传入的值
	 * @return {@link List}<{@link T}>
	 */
	@SafeVarargs
	public static <T> List<T> newArrayList(T... values) {
		if (CollectionUtils.isEmpty(Arrays.asList(values))) {
			return new ArrayList<>();
		} else {
			Collections.addAll(new ArrayList<>(values.length), values);
			return new ArrayList<>();
		}
	}

	/**
	 * 创建一个空的set 或者根据传入的值创建set
	 * @param values 传入的值
	 * @return {@link Set}<{@link T}>
	 */
	@SafeVarargs
	public static <T> Set<T> newHashSet(T... values) {
		if (CollectionUtils.isEmpty(Arrays.asList(values))) {
			return new HashSet<>();
		} else {
			Collections.addAll(new HashSet<>(values.length), values);
			return new HashSet<>();
		}
	}

	/**
	 * 创建一个空的hashmap
	 * @return {@link HashMap}<{@link K}, {@link V}>
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap();
	}

	/**指定长度创建hashmap
	 * @param size
	 * @return {@link HashMap}<{@link K}, {@link V}>
	 */
	public static <K, V> HashMap<K, V> newHashMap(int size) {
		return newHashMap(size, false);
	}

	/** 创建一个hashmap
	 * @param size 长度
	 * @param isLinked 是否是linkedHashMap
	 * @return {@link HashMap}
	 */
	public static <K, V> HashMap newHashMap(int size, boolean isLinked) {
		int initialCapacity = (int)((float)size / 0.75F) + 1;
		return (HashMap)(isLinked ? new LinkedHashMap(initialCapacity) : new HashMap(initialCapacity));
	}

	/**
	 * 对象是否为数组对象
	 *
	 * @param obj 对象
	 * @return 是否为数组对象，如果为{@code null} 返回false
	 */
	public static boolean isArray(Object obj) {
		if (null == obj) {
			return false;
		}
		return obj.getClass().isArray();
	}

	/**
	 * Determine whether the given Collection is not empty:
	 * i.e. {@code null} or of zero length.
	 *
	 * @param coll the Collection to check
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Collection<?> coll) {
		return !CollectionUtils.isEmpty(coll);
	}

	/**
	 * Determine whether the given Map is not empty:
	 * i.e. {@code null} or of zero length.
	 *
	 * @param map the Map to check
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
		return !CollectionUtils.isEmpty(map);
	}

}
