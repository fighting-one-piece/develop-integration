package org.cisiondata.modules.auth.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.cisiondata.utils.redis.RedisClusterUtils;

public class RedisCacheManager implements CacheManager {

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache();
	}

	@SuppressWarnings("rawtypes")
	static class RedisCache implements Cache {

		@Override
		public Object get(Object key) throws CacheException {
			return RedisClusterUtils.getInstance().get(key);
		}

		@Override
		public Object put(Object key, Object value) throws CacheException {
			RedisClusterUtils.getInstance().set(key, value);
			return value;
		}

		@Override
		public Object remove(Object key) throws CacheException {
			return RedisClusterUtils.getInstance().delete(key);
		}

		@Override
		public int size() {
			return 0;
		}
		
		@Override
		public Set keys() {
			return null;
		}

		@Override
		public Collection values() {
			return null;
		}
		
		@Override
		public void clear() throws CacheException {
			
		}
		
	}

}
