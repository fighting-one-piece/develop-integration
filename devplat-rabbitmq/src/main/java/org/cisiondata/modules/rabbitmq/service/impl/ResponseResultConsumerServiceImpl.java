package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("responseResultConsumerService")
public class ResponseResultConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		System.out.println("response result consumer message: " + message);
	}

}
