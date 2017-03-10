package org.cisiondata.modules.queue.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.queue.service.IRedisMQService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service("redisMQService")
public class RedisMQServiceImpl implements IRedisMQService {
	
	@Resource(name="jedisCluster")
	private JedisCluster jedisCluster = null;
	
	@Override
	public void sendMessage(String routingKey, Object message) {
		jedisCluster.rpush(SerializerUtils.write(routingKey), SerializerUtils.write(message));
	}

	@Override
	public Object receiveMessage(String routingKey) {
		byte[] value = jedisCluster.lpop(SerializerUtils.write(routingKey));
		return null == value || value.length == 0 ? null : SerializerUtils.read(value);
	}

}
