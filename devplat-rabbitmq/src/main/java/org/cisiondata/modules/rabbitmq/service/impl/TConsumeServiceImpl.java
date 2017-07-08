package org.cisiondata.modules.rabbitmq.service.impl;

import org.cisiondata.modules.rabbitmq.service.impl.ConsumeServiceImpl;

//@Service("TConsumeService")
public class TConsumeServiceImpl extends ConsumeServiceImpl {

	@Override
	protected String getRoutingKey() {
		return "topic.*";
	}

	@Override
	public void handleMessage(Object message) {
		LOG.info("topic consume receive message: " + message);
		System.out.println("topic consume receive message: " + message);
	}

}
