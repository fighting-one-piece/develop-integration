package org.cisiondata.modules.auth.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.utils.endecrypt.Base64Utils;
import org.cisiondata.utils.endecrypt.IDUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.stereotype.Component;

/** 
 * 基于redis缓存实现的会话处理器, 达到分布式环境会话一致的效果
 **/
@Component("redisStorageHandler")
public class RedisStorageHandler implements StorageHandler {
	
	private static final long serialVersionUID = 1L;
	
	private int valueTTL = 24 * 3600;
	private String keyPrefix = "cisiondata";

	private String sessionKey(String sessionId) {
		return keyPrefix + ":session:id:" + sessionId;
	}

	private byte[] hashKey(String sessionId) {
		return (keyPrefix + ":session:" + sessionId).getBytes();
	}

	private String firstFlagKey(String sessionId, String name) {
		return keyPrefix + ":session:first:" + sessionId + ":" + name;
	}

	@Override
	public String createSessionId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionId = Base64Utils.encode(IDUtils.genUUID());
		RedisClusterUtils.getInstance().set(sessionKey(sessionId), "1", valueTTL);
		return sessionId;
	}

	@Override
	public boolean existsSessionId(String sessionId) throws Exception {
		String key = sessionKey(sessionId);
		String value = (String) RedisClusterUtils.getInstance().get(key);
		return "1".equals(value);
	}

	@Override
	public void initialize(String sessionId) throws Exception {
		String key = sessionKey(sessionId);
		RedisClusterUtils.getInstance().set(key, "1", valueTTL);
	}

	@Override
	public void invalidate(String sessionId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String key = sessionKey(sessionId);
		RedisClusterUtils.getInstance().delete(key);
		byte[] hashKey = hashKey(sessionId);
		RedisClusterUtils.getInstance().delete(hashKey);
	}

	@Override
	public void setAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name, Object value) throws Exception {
		byte[] hashKey = hashKey(sessionId);
		if (value == null) {
			RedisClusterUtils.getInstance().getJedisCluster().hdel(hashKey, name.getBytes());
		} else {
			RedisClusterUtils.getInstance().getJedisCluster().hset(hashKey, name.getBytes(), SerializerUtils.write(value));
		}
		RedisClusterUtils.getInstance().getJedisCluster().expire(hashKey, valueTTL);
	}

	@Override
	public Object getAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception {
		byte[] hashKey = hashKey(sessionId);
		return SerializerUtils.read(RedisClusterUtils.getInstance().getJedisCluster().hget(hashKey, name.getBytes()));
	}

	@Override
	public void removeAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception {
		byte[] hashKey = hashKey(sessionId);
		RedisClusterUtils.getInstance().getJedisCluster().hdel(hashKey, name.getBytes());
	}

	@Override
	public Object getAttributeAndRemove(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception {
		String first = firstFlagKey(sessionId, name);
		String flag = RedisClusterUtils.getInstance().getJedisCluster().getSet(first, System.currentTimeMillis() + "");
		if (flag == null || (System.currentTimeMillis() - Long.parseLong(flag) > 5 * 60 * 1000)) {
			byte[] hashKey = hashKey(sessionId);
			Object result = SerializerUtils.read(RedisClusterUtils.getInstance().getJedisCluster().hget(hashKey, name.getBytes()));
			RedisClusterUtils.getInstance().getJedisCluster().hdel(hashKey, name.getBytes());
			RedisClusterUtils.getInstance().getJedisCluster().del(first);
			return result;
		} 
		return null;
	}

}
