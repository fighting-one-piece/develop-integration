package org.cisiondata.modules.queue.service.impl;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("rdefaultConsumerService")
public class DefaultConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.DEFAULT_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("default consumer receive message: " + message);
	}

}
