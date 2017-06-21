package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.entity.CQueue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("defaultConsumerService")
public class DefaultConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return CQueue.DEFAULT_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("default consumer receive message: " + message);
		System.out.println("default consumer receive message: " + message);
	}

}
