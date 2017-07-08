package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.service.impl.ConsumeServiceImpl;
import org.springframework.stereotype.Service;

@Service("CConsumeService")
public class CConsumeServiceImpl extends ConsumeServiceImpl {

	@Override
	protected String getRoutingKey() {
		return "c-routingkey";
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("c-routingkey consume receive message: " + message);
		System.out.println("c-routingkey consume receive message: " + message);
	}

}
