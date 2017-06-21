package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("TConsumerService")
public class TConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return "topic.*";
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("topic consumer receive message: " + message);
		System.out.println("topic consumer receive message: " + message);
	}

}
