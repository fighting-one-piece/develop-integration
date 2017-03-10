package org.cisiondata.modules.queue.service;

public interface IRedisMQService {

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
	
}
