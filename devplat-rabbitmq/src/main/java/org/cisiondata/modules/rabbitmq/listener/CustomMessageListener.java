package org.cisiondata.modules.rabbitmq.listener;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.rabbitmq.service.IConsumerService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customMessageListener")
public class CustomMessageListener implements MessageListener {
	
	@Autowired(required=true)
	private List<IConsumerService> consumerServiceList = null;
	
	@Override
	public void onMessage(Message message) {
		System.out.println(message);
		MessageProperties properties = message.getMessageProperties();
		String exchange = properties.getReceivedExchange();
		String routingKey = properties.getReceivedRoutingKey();
		if (StringUtils.isBlank(routingKey)) return;
		for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
			consumerServiceList.get(i).handleMessage(exchange, routingKey, 
					SerializerUtils.read(message.getBody()));
		}
	}
	


}
