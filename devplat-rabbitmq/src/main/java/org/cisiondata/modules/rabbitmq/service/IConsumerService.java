package org.cisiondata.modules.rabbitmq.service;

public interface IConsumerService {
	
	/**
	 * 处理指定队列消息
	 * @param message
	 */
	public void handleMessage(String exchange, String routingKey, Object message);
	
}
