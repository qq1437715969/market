package com.market.safe.config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.market.core.constant.TimeConstant;


/**
 * 缓存类,少需要的自己加
 * @author ls
 * @2017年12月26日
 */
@Component
public class CacheClient {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private ValueOperations<String, Object> strRedis;
	
	@PostConstruct
	public void init() {
		strRedis = redisTemplate.opsForValue();	
	}
			
	/**
	 * 直接添加，默认过期时间为2天
	 * @param key
	 * @param bean
	 */
	public void set(String key,Object obj) {
		strRedis.set(key, obj,TimeConstant.DAY*2,TimeUnit.MILLISECONDS);			
	}
	
	/**
	 * 添加，
	 * @param key
	 * @param bean
	 * @param time  过期时间
	 */
	public void set(String key,Object obj,long timeout) {	
		strRedis.set(key, obj,timeout,TimeUnit.MILLISECONDS);		
	}
	
	/**
	 * 不存在则添加，存在则不做任务操作
	 * @param key
	 * @param bean
	 * @param time
	 * @return
	 */
	public boolean add(String key,Object obj) {
		return strRedis.setIfAbsent(key, obj);
	}
	
	public Object get(String key) {
		return strRedis.get(key);
	}
	
	public boolean isExist(String key) {	
		 Long size = strRedis.size(key);
		 return (size!=null &&size>0);
	}
	
	/**
	 * 批量添加
	 * @param map
	 * @param time
	 */
	public void setAll(Map<String,Object> map,long timeout) {
		
		for(Map.Entry<String,Object> entry: map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			strRedis.set(key, value, timeout, TimeUnit.MILLISECONDS);	
		}
	}
	
	/**
	 * 移除某个key
	 */
	public void removeKey(String key) {
		strRedis.set(key, "", TimeConstant.EXPIRE, TimeUnit.MILLISECONDS);
	}
	
}
