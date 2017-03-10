package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("requestAccessConsumerService")
public class RequestAccessConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		System.out.println("request access consumer message: " + message);
	}

}
