package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("CConsumerService")
public class CConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return "c-routingkey";
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("c-routingkey consumer receive message: " + message);
		System.out.println("c-routingkey consumer receive message: " + message);
	}

}
