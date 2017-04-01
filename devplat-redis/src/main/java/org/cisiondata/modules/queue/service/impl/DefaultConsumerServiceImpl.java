package org.cisiondata.modules.queue.service.impl;

import org.cisiondata.modules.queue.entity.MQueue;
import org.cisiondata.modules.queue.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DefaultConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.DEFAULT_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
	}

}
