package org.cisiondata.modules.rabbitmq.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.rabbitmq.service.IMQService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service("mqService")
public class MQServiceImpl implements IMQService {

	@Resource(name="rAmqpTemplate")
    private AmqpTemplate rAmqpTemplate = null;
	
	@Resource(name="tAmqpTemplate")
    private AmqpTemplate tAmqpTemplate = null;
	
	@Override
	public void sendMessage(Object message) {
		rAmqpTemplate.convertAndSend(message);
	}
	
	@Override
	public void sendTopicMessage(Object message) {
		tAmqpTemplate.convertAndSend(message);
	}

	@Override
	public void sendMessage(String routingKey, Object message) {
		rAmqpTemplate.convertAndSend(routingKey, message);
	}
	
	@Override
	public void sendTopicMessage(String routingKey, Object message) {
		tAmqpTemplate.convertAndSend(routingKey, message);
	}

	@Override
	public void sendMessage(String exchange, String routingKey, Object message) {
		rAmqpTemplate.convertAndSend(exchange, routingKey, message);
	}

	@Override
	public Object receiveMessage(String queueName) {
		return rAmqpTemplate.receiveAndConvert(queueName);
	}

	@Override
	public Object receiveMessage(String queueName, long timeout) {
		return rAmqpTemplate.receiveAndConvert(queueName, timeout);
	}

	
}
