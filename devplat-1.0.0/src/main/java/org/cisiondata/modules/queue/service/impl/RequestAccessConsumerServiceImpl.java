package org.cisiondata.modules.queue.service.impl;

import org.cisiondata.modules.rabbitmq.service.Queue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("requestAccessConsumerService")
public class RequestAccessConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return Queue.REQUEST_ACCESS_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		
	}

}
