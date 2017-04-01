package org.cisiondata.modules.queue.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.queue.service.IRedisMQService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service("redisMQService")
public class RedisMQServiceImpl implements IRedisMQService {
	
	@Resource(name="jedisCluster")
	private JedisCluster jedisCluster = null;
	
	private static Set<String> routingKeys = new HashSet<String>();
	
	@Override
	public Set<String> getRoutingKeys() {
		return routingKeys;
	}
	
	@Override
	public void sendMessage(String routingKey, Object message) {
		if (!routingKeys.contains(routingKey)) routingKeys.add(routingKey);
		jedisCluster.rpush(SerializerUtils.write(routingKey), SerializerUtils.write(message));
	}

	@Override
	public Object receiveMessage(String routingKey) {
		byte[] value = jedisCluster.lpop(SerializerUtils.write(routingKey));
		return null == value || value.length == 0 ? null : SerializerUtils.read(value);
	}
	
	@Override
	public long readMessageQueueLength(String routingKey) {
		return jedisCluster.llen(SerializerUtils.write(routingKey));
	}

}
