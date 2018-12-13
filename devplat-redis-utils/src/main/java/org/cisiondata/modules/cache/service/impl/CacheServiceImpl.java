package org.cisiondata.modules.cache.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cisiondata.modules.cache.service.ICacheService;
import org.cisiondata.utils.redis.RedisClusterUtils;

public class CacheServiceImpl implements ICacheService {
	
	@Override
	public Set<String> readKeys(String pattern) throws RuntimeException {
		return RedisClusterUtils.getInstance().keys(pattern);
	}
	
	@Override
	public Object readValueByKey(String key) throws RuntimeException {
		String type = RedisClusterUtils.getInstance().type(key);
		Object returnObj = null;
		switch (type) {
			case "string" : returnObj = RedisClusterUtils.getInstance().get(key); break;
			case "hash" : returnObj = RedisClusterUtils.getInstance().hgetAll(key); break;
			case "zset" : {
				returnObj = RedisClusterUtils.getInstance().zrevrangeWithScores(key, 0, 100);
				break;
			}
			case "none" : break;
		}
		return returnObj;
	}
	
	@Override
	public Object readValueByNotSerKey(String key) throws RuntimeException {
		String type = RedisClusterUtils.getInstance().getJedisCluster().type(key);
		Object returnObj = null;
		switch (type) {
			case "string" : returnObj = RedisClusterUtils.getInstance().getJedisCluster().get(key); break;
			case "hash" : returnObj = RedisClusterUtils.getInstance().getJedisCluster().hgetAll(key); break;
			case "zset" : {
				returnObj = RedisClusterUtils.getInstance().getJedisCluster().zrevrangeWithScores(key, 0, 100);
				break;
			}
			case "none" : break;
		}
		return returnObj;
	}
	
	@Override
	public Object readValuesByKey(String pattern) throws RuntimeException {
		if ("*".equals(pattern)) throw new RuntimeException("pattern must not be *");
		if (pattern.indexOf("*") == -1) pattern = "*" + pattern + "*";
		Set<String> keys = RedisClusterUtils.getInstance().keys(pattern);
		if (null != keys && keys.size() > 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			for (String key : keys) {
				result.put(key, readValueByKey(key));
			}
			return result;
		}
		return null;
	}
	
	@Override
	public Long deleteNotSerKey(String key) throws RuntimeException {
		return RedisClusterUtils.getInstance().getJedisCluster().del(key);
	}

	@Override
	public int deleteKeys(String pattern) throws RuntimeException {
		if ("*".equals(pattern)) throw new RuntimeException("pattern must not be *");
		if (pattern.indexOf("*") == -1) pattern = "*" + pattern + "*";
		Set<String> keys = RedisClusterUtils.getInstance().keys(pattern);
		int deleteCount = 0;
		if (null != keys && keys.size() > 0) {
			for (String key : keys) {
				if (RedisClusterUtils.getInstance().delete(key) > 0) {	
					deleteCount++;
				}
			}
		}
		return deleteCount;
	}
	
}
