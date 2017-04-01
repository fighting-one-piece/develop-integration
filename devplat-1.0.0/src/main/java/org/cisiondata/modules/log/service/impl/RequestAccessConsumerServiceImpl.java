package org.cisiondata.modules.log.service.impl;

import org.cisiondata.modules.queue.entity.MQueue;
import org.cisiondata.modules.queue.entity.RequestMessage;
import org.cisiondata.modules.queue.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RequestAccessConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			System.out.println(" url: " + requestMessage.getUrl());
			System.out.println(" params: " + requestMessage.getParams());
			System.out.println(" ip: " + requestMessage.getIpAddress());
			System.out.println(" account: " + requestMessage.getAccount());
			System.out.println(" time: " + requestMessage.getTime());
			System.out.println(" result: " + requestMessage.getReturnResult());
		}
	}

}
