package org.cisiondata.modules.queue.service.impl;

import org.cisiondata.modules.auth.entity.RequestMessage;
import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.impl.ConsumerServiceImpl;
import org.springframework.stereotype.Service;

@Service("rrequestAccessConsumerService")
public class RequestAccessConsumerServiceImpl extends ConsumerServiceImpl {

	@Override
	protected String getRoutingKey() {
		return MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey();
	}

	@Override
	public void handleMessage(Object message) {
		RequestMessage requestMessage = (RequestMessage) message;
		LOG.info("request url: {}", requestMessage.getUrl());
		LOG.info("request params: {}", requestMessage.getParams());
		LOG.info("request ip: {}", requestMessage.getIpAddress());
		LOG.info("request account: {}", requestMessage.getAccount());
		LOG.info("request time: {}", requestMessage.getTime());
		LOG.info("request result: {}", requestMessage.getReturnResult());
	}

}
