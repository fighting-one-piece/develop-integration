package org.cisiondata.modules.rabbitmq.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mqMiddleware")
public class MQMiddleware implements MessageListener {
	
	@Autowired(required=true)
	private List<IConsumerService> consumerServiceList = null;
	
	@Override
	public void onMessage(Message message) {
		MessageProperties properties = message.getMessageProperties();
		String routingKey = properties.getReceivedRoutingKey();
		System.out.println("routingKey: " + routingKey);
		if (StringUtils.isBlank(routingKey)) return;
		for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
			consumerServiceList.get(i).handleMessage(routingKey, 
					SerializerUtils.read(message.getBody()));
		}
	}
	


}
