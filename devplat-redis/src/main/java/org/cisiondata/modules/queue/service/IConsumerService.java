package org.cisiondata.modules.queue.service;

public interface IConsumerService {
	
	/**
	 * 处理指定队列消息
	 * @param message
	 */
	public void handleMessage(String routingKey, Object message);
	
}
