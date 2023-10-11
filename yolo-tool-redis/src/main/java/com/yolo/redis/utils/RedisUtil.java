package com.yolo.redis.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@AllArgsConstructor
public class RedisUtil {

	private RedisTemplate<String, Object> redisTemplate;

	/**
	 *   common   部分
	 */
	//========================删除==========================
	//阻塞删除
	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}
	//阻塞删除、批量
	public Long delete(Collection<String> keys) {
		return redisTemplate.delete(keys);
	}

	//非阻塞删除，另开线程处理，对于大型LIST或HASH的分配太多,它会长时间阻塞Redis，可以用该方法
	public Boolean unlink(String key) {
		return redisTemplate.unlink(key);
	}
	//非阻塞删除、批量
	public Long unlink(Collection<String> keys) {
		return redisTemplate.unlink(keys);
	}

	//========================修改==========================
	//设置key的过期时间
	public Boolean expire(String key, long time, TimeUnit timeUnit) {
		return redisTemplate.expire(key, time, timeUnit);
	}

	public void expire(String key, Duration duration) {
		redisTemplate.expire(key, duration);
	}

	//设置key在指定Date时间之后过期
	public Boolean expireAt(String key, Date date) {
		return redisTemplate.expireAt(key, date);
	}

	//移除key的过期时间，使key永不过期
	public Boolean persist(String key) {
		return redisTemplate.persist(key);
	}

	//重命名key
	public void rename(String oldKey, String newKey) {
		redisTemplate.rename(oldKey, newKey);
	}

	//newKey不存在才重命名，存在不操作
	public Boolean renameIfAbsent(String oldKey, String newKey) {
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	//========================判断==========================
	//判断key是否存在
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	//========================获取==========================
	//根据匹配规则获取key集合
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}
	//获取过期时间
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}
	//获取过期时间并转换成对应的时间单位
	public Long getExpire(String key, TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}
	//获取数据类型
	public DataType type(String key) {
		return redisTemplate.type(key);
	}

	//随机获得一个key
	public String randomKey() {
		return redisTemplate.randomKey();
	}

	/**
	 *   String   部分
	 */
	//========================添加==========================
	//存储key=value键值对
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key,value);
	}
	//存储key=value键值对并设置过期时间
	public void set(String key, Object value, long time, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, time, timeUnit);
	}

	public void set(String key, Object value, Duration duration) {
		redisTemplate.opsForValue().set(key, value, duration);
	}

	//key不存在才存储，存在不操作
	public Boolean setIfAbsent(String key, Object value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	public Boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
	}

	public Boolean setIfAbsent(String key, Object value, Duration duration) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, duration);
	}

	//key存在才存储，不存在不操作
	public Boolean setIfPresent(String key, Object value) {
		return redisTemplate.opsForValue().setIfPresent(key, value);
	}

	public Boolean setIfPresent(String key, Object value, long time, TimeUnit timeUnit) {
		return redisTemplate.opsForValue().setIfPresent(key, value, time, timeUnit);
	}

	public Boolean setIfPresent(String key, Object value, Duration duration) {
		return redisTemplate.opsForValue().setIfPresent(key, value, duration);
	}

	//设置新值并返回旧值
	public Object getAndSet(String key, Object newValue) {
		return redisTemplate.opsForValue().getAndSet(key, newValue);
	}

	//========================获取==========================
	//批量存储
	public void multiSet(Map<String, Object> map) {
		redisTemplate.opsForValue().multiSet(map);
	}

	public Boolean multiSetIfAbsent(Map<String, Object> map) {
		return redisTemplate.opsForValue().multiSetIfAbsent(map);
	}

	//根据key获取value
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	public List<Object> multiGet(Collection<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	//========================修改==========================
	//value值自增(+1)
	public Long increment(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	//value值自增(incValue)
	public Long increment(String key, long incValue) {
		return redisTemplate.opsForValue().increment(key, incValue);
	}

	public Double increment(String key, double incValue) {
		return redisTemplate.opsForValue().increment(key, incValue);
	}

	//value值自减(-1)
	public Long decrease(String key){
		return redisTemplate.opsForValue().decrement(key);
	}

	//value值自减(incValue)
	public Long decrease(String key, long incValue) {
		return redisTemplate.opsForValue().decrement(key, incValue);
	}

	/**
	 *   hash   部分
	 */
	//========================添加==========================
	//存储元素
	public void put(String k, String hk, Object hv) {
		redisTemplate.opsForHash().put(k, hk, hv);
	}

	public void putAll(String k, Map<String, Object> map) {
		redisTemplate.opsForHash().putAll(k, map);
	}

	//hk不存在才会插入元素
	public Boolean putIfAbsent(String k, String hk, Object hv) {
		return redisTemplate.opsForHash().putIfAbsent(k, hk, hv);
	}

	//========================获取==========================
	//获取元素
	public Object get(String k, String hk) {
		return redisTemplate.opsForHash().get(k, hk);
	}

	public List<Object> multiGet(String k, Collection<String> hk) {
		return redisTemplate.opsForHash().multiGet(k, Collections.singleton(hk));
	}

	public Set<Object> hKeys(String k) {
		return redisTemplate.opsForHash().keys(k);
	}

	public List<Object> values(String k) {
		return redisTemplate.opsForHash().values(k);
	}

	public Map<Object, Object> entries(String k) {
		return redisTemplate.opsForHash().entries(k);
	}

	public Long size(String k) {
		return redisTemplate.opsForHash().size(k);
	}

	//========================修改==========================
	public Long increment(String k, String hk, long inc) {
		return redisTemplate.opsForHash().increment(k, hk, inc);
	}

	public Double increment(String k, String hk, double inc) {
		return redisTemplate.opsForHash().increment(k, hk, inc);
	}

	//========================删除==========================
	public Long delete(String k, Object... hvs) {
		return redisTemplate.opsForHash().delete(k, hvs);
	}

	//========================判断==========================
	public Boolean hasKey(String k, Object hv) {
		return redisTemplate.opsForHash().hasKey(k, hv);
	}

	/**
	 *   set   部分
	 */
	//========================添加==========================
	//添加元素，返回成功添加个数
	public Long add(String key, Object... vars) {
		return redisTemplate.opsForSet().add(key, vars);
	}

	//========================获取==========================
	//获取元素
	public Set<Object> members(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	//随机获取一个元素
	public Object randomMember(String key) {
		return redisTemplate.opsForSet().randomMember(key);
	}

	//随机获取count个数量的元素(元素会重复)
	public List<Object> randomMembers(String key, long count) {
		return redisTemplate.opsForSet().randomMembers(key, count);
	}

	//随机获取count个数量的元素(元素不重复)
	public Set<Object> distinctRandomMembers(String key, long count) {
		return redisTemplate.opsForSet().distinctRandomMembers(key, count);
	}

	//set集合弹出一个元素，集合元素个数减一
	public Object pop(String key) {
		return redisTemplate.opsForSet().pop(key);
	}

	public List<Object> pop(String key, long count) {
		return redisTemplate.opsForSet().pop(key, count);
	}

	//集合大小
	public Long sLen(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	//========================移除==========================
	//移除元素，返回成功移除个数
	public Long remove(String key, Object... vars) {
		return redisTemplate.opsForSet().remove(key, vars);
	}

	//转移
	public Boolean move(String key1, Object var, String key2) {
		return redisTemplate.opsForSet().move(key1, var, key2);
	}

	//========================判断==========================
	//判断set集合是否有var元素
	public Boolean isMember(String key, Object var) {
		return redisTemplate.opsForSet().isMember(key, var);
	}

	//========================合集==========================
	//把所有集合的元素加在一起，然后去重
	public Set<Object> union(String key1, String key2) {
		return redisTemplate.opsForSet().union(key1, key2);
	}

	public Set<Object> union(String key1, Collection<String> keys) {
		return redisTemplate.opsForSet().union(key1, keys);
	}

	public Set<Object> union(Collection<String> keys) {
		return redisTemplate.opsForSet().union(keys);
	}

	public Long unionAndStore(String key1, String key2, String dest) {
		return redisTemplate.opsForSet().unionAndStore(key1, key2, dest);
	}

	public Long unionAndStore(String key1, Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().unionAndStore(key1, keys, dest);
	}

	public Long unionAndStore(Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().unionAndStore(keys, dest);
	}

	//========================交集==========================
	//所有集合都共有的元素
	public Set<Object> intersect(String key1, String key2) {
		return redisTemplate.opsForSet().intersect(key1, key2);
	}

	public Set<Object> intersect(String key1, Collection<String> keys) {
		return redisTemplate.opsForSet().intersect(key1, keys);
	}

	public Set<Object> intersect(Collection<String> keys) {
		return redisTemplate.opsForSet().intersect(keys);
	}

	public Long intersectAndStore(String key1, String key2, String dest) {
		return redisTemplate.opsForSet().intersectAndStore(key1, key2, dest);
	}

	public Long intersectAndStore(String key1, Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().intersectAndStore(key1, keys, dest);
	}

	public Long intersectAndStore(Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().intersectAndStore(keys, dest);
	}


	//========================差集==========================
	//以第一个集合为准，去除与其他集合共同的元素，最后只留下自身独有的元素
	public Set<Object> difference(String key1, String key2) {
		return redisTemplate.opsForSet().difference(key1, key2);
	}

	public Set<Object> difference(String key1, Collection<String> keys) {
		return redisTemplate.opsForSet().difference(key1, keys);
	}

	public Set<Object> difference(Collection<String> keys) {
		return redisTemplate.opsForSet().difference(keys);
	}

	public Long differenceAndStore(String key1, String key2, String dest) {
		return redisTemplate.opsForSet().differenceAndStore(key1, key2, dest);
	}

	public Long differenceAndStore(String key1, Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().differenceAndStore(key1, keys, dest);
	}

	public Long differenceAndStore(Collection<String> keys, String dest) {
		return redisTemplate.opsForSet().differenceAndStore(keys, dest);
	}

	/**
	 *   list   部分
	 */
	//========================添加==========================
	//左插入元素，返回list元素个数
	public Long leftPush(String key, Object var) {
		return redisTemplate.opsForList().leftPush(key, var);
	}

	public Long leftPushAll(String key, Object... vars) {
		return redisTemplate.opsForList().leftPushAll(key, vars);
	}

	public Long leftPushAll(String key, Collection<Object> vars) {
		return redisTemplate.opsForList().leftPushAll(key, vars);
	}

	public Long leftPushIfPresent(String key, Object var) {
		return redisTemplate.opsForList().leftPushIfPresent(key, var);
	}

	//在元素var1的左边插入var2元素，返回list元素个数；元素var1不存在，不插入并返回-1
	public Long leftPush(String key, Object var1, Object var2) {
		return redisTemplate.opsForList().leftPush(key, var1, var2);
	}

	//右插入元素，返回list元素个数
	public Long rightPush(String key, Object var) {
		return redisTemplate.opsForList().rightPush(key, var);
	}

	public Long rightPushAll(String key, Object... vars) {
		return redisTemplate.opsForList().rightPushAll(key, vars);
	}

	public Long rightPushAll(String key, Collection<Object> vars) {
		return redisTemplate.opsForList().rightPushAll(key, vars);
	}

	public Long rightPushIfPresent(String key, Object var) {
		return redisTemplate.opsForList().rightPushIfPresent(key, var);
	}

	//在元素var1的右边插入var2元素，返回list元素个数；元素var1不存在，不插入并返回-1
	public Long rightPush(String key, Object var1, Object var2) {
		return redisTemplate.opsForList().rightPush(key, var1, var2);
	}

	//========================获取==========================
	//获取index对应的元素
	public Object index(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	//获取下标区间[start, end]的元素;[0,-1]获取所有元素
	public List<Object> range(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	public Long lLen(String key) {
		return redisTemplate.opsForList().size(key);
	}

	//========================修改==========================
	//更新下标index对应的元素值
	public void set(String key, long index, Object var) {
		redisTemplate.opsForList().set(key, index, var);
	}

	//========================移除==========================
	//根据count删除var值的元素，返回删除个数
	//count=0,删除所有var值的元素
	//|count|>count(var),删除所有var值的元素
	//count>0,正序删除count个数量的var元素
	//count<0,反序删除count个数量的var元素
	public Long remove(String key, long count, Object var) {
		return redisTemplate.opsForList().remove(key, count, var);
	}

	//移除不在下标区间[start, end]内的元素
	public void trim(String key, long start, long end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	//左弹出一个元素
	public Object leftPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	//右弹出一个元素
	public Object rightPop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

	//key1右弹出，key2左插入
	public Object rightPopAndLeftPush(String key1, String key2) {
		return redisTemplate.opsForList().rightPopAndLeftPush(key1, key2);
	}

	/**
	 *   zset   部分
	 */
	//========================添加==========================
	public Boolean add(String key, Object var, double score) {
		return redisTemplate.opsForZSet().add(key, var, score);
	}

	public Long add(String key, Set<ZSetOperations.TypedTuple<Object>> vars) {
		return redisTemplate.opsForZSet().add(key, vars);
	}

	//========================获取==========================
	//获取var元素对应的下标
	public Long rank(String key, Object var) {
		return redisTemplate.opsForZSet().rank(key, var);
	}

	public Long reverseRank(String key, Object var) {
		return redisTemplate.opsForZSet().reverseRank(key, var);
	}

	//正序
	//根据下标区间[start,end]获取元素var
	public Set<Object> zRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}

	//根据下标区间[start,end]获取元素var和score
	public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
		return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}

	//根据score区间[min,max]获取元素var
	public Set<Object> rangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	//根据score区间[min,max]获取元素var和score
	public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	//根据score区间[min,max]获取元素var，跳过offset个，最后只拿count个
	public Set<Object> rangeByScore(String key, double min, double max, long offset, long count) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
	}

	//根据score区间[min,max]获取元素var和score，跳过offset个，最后只拿count个
	public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Object> rangeByLex(String key, RedisZSetCommands.Range range) {
		return redisTemplate.opsForZSet().rangeByLex(key, range);
	}

	public Set<Object> rangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
		return redisTemplate.opsForZSet().rangeByLex(key, range, limit);
	}

	//反序
	//根据下标区间[start,end]获取元素var
	public Set<Object> reverseRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	//根据下标区间[start,end]获取元素var和score
	public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
		return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
	}

	//根据score区间[min,max]获取元素var
	public Set<Object> reverseRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
	}

	//根据score区间[min,max]获取元素var和score
	public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max) {
		return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
	}

	//根据score区间[min,max]获取元素var，跳过offset个，最后只拿count个
	public Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
	}

	//根据score区间[min,max]获取元素var和score，跳过offset个，最后只拿count个
	public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
		return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Object> reverseRangeByLex(String key, RedisZSetCommands.Range range) {
		return redisTemplate.opsForZSet().rangeByLex(key, range);
	}

	public Set<Object> reverseRangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
		return redisTemplate.opsForZSet().rangeByLex(key, range, limit);
	}

	//获取score区间[mix,max]的元素个数
	public Long count(String key, double min, double max) {
		return redisTemplate.opsForZSet().count(key, min, max);
	}

	public Long zLen(String key) {
		return redisTemplate.opsForZSet().size(key);
	}

	//获取score
	public Double score(String key, Object var) {
		return redisTemplate.opsForZSet().score(key, var);
	}

	//========================修改==========================
	public Double incrementScore(String key, Object var, double score) {
		return redisTemplate.opsForZSet().incrementScore(key, var, score);
	}

	//========================删除==========================
	public Long zRemove(String key, Object... vars) {
		return redisTemplate.opsForZSet().remove(key, vars);
	}

	public Long removeRange(String key, long start, long end) {
		return redisTemplate.opsForZSet().removeRange(key, start, end);
	}

	public Long removeRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
	}

	//========================合集==========================
	public Long zUnionAndStore(String key1, String key2, String dest) {
		return redisTemplate.opsForZSet().unionAndStore(key1, key2, dest);
	}

	public Long zUnionAndStore(String key1, Collection<String> keys, String dest) {
		return redisTemplate.opsForZSet().unionAndStore(key1, keys, dest);
	}

	public Long unionAndStore(String key1, Collection<String> keys, String dest, RedisZSetCommands.Aggregate aggregate) {
		return redisTemplate.opsForZSet().unionAndStore(key1, keys, dest, aggregate);
	}

	public Long unionAndStore(String key1, Collection<String> keys, String dest, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
		return redisTemplate.opsForZSet().unionAndStore(key1, keys, dest, aggregate, weights);
	}

	//========================交集==========================
	public Long zIntersectAndStore(String key1, String key2, String dest) {
		return redisTemplate.opsForZSet().intersectAndStore(key1, key2, dest);
	}

	public Long zIntersectAndStore(String key1, Collection<String> keys, String dest) {
		return redisTemplate.opsForZSet().intersectAndStore(key1, keys, dest);
	}

	public Long intersectAndStore(String key1, Collection<String> keys, String dest, RedisZSetCommands.Aggregate aggregate) {
		return redisTemplate.opsForZSet().intersectAndStore(key1, keys, dest, aggregate);
	}

	public Long intersectAndStore(String key1, Collection<String> keys, String dest, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
		return redisTemplate.opsForZSet().intersectAndStore(key1, keys, dest, aggregate, weights);
	}

}
