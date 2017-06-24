package org.cisiondata.modules.rabbitmq.consumer;

import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.stereotype.Component;

/**
 * messageListenerContainer 添加
 * <property name="consumerTagStrategy" ref="consumerTagStrategy" /> 
 */
@Component
public class CustomConsumerTagStrategy implements ConsumerTagStrategy {
	
	@Override
	public String createConsumerTag(String queue) {
		return "consumer" + "_" + queue;
	}

}
