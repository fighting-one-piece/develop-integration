package org.cisiondata.modules.queue.service;

import java.util.Set;

public interface IRedisMQService {
	
	/**
	 * 获取routingKeys
	 * @return
	 */
	public Set<String> getRoutingKeys();

	/**
	 * 发送消息
	 * @param routingKey
	 * @param message
	 */
	public void sendMessage(String routingKey, Object message);
	
	/**
	 * 接收消息
	 * @param routingKey
	 * @return
	 */
	public Object receiveMessage(String routingKey);
	
	/**
	 * 读取消息队列长度
	 * @param routingKey
	 * @return
	 */
	public long readMessageQueueLength(String routingKey);
	
}
