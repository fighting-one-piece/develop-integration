package org.cisiondata.modules.rabbitmq.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.IMQService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service("mqService")
public class MQServiceImpl implements IMQService {
	
	@Resource(name="amqpAdmin")
	private AmqpAdmin amqpAdmin = null;

	@Resource(name="rAmqpTemplate")
    private AmqpTemplate rAmqpTemplate = null;
	
	@Resource(name="tAmqpTemplate")
    private AmqpTemplate tAmqpTemplate = null;
	
    @Override
	public void declareExchange(Exchange exchange) {
		amqpAdmin.declareExchange(exchange);
	}
    
    @Override
    public String declareQueue(Queue queue) {
    	return amqpAdmin.declareQueue(queue);
    }
    
    @Override
    public void declareBinding(Binding binding) {
    	amqpAdmin.declareBinding(binding);
    }
    
	@Override
	public void sendMessage(Object message) {
		rAmqpTemplate.convertAndSend(MQueue.DEFAULT_QUEUE.getRoutingKey(), message);
	}
	
	@Override
	public void sendTopicMessage(Object message) {
		tAmqpTemplate.convertAndSend(message);
	}

	@Override
	public void sendMessage(String routingKey, Object message) {
		System.out.println("routingKey: " + routingKey + " message: " + message);
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
