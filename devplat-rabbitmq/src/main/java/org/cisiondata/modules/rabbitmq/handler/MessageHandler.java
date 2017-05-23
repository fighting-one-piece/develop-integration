package org.cisiondata.modules.rabbitmq.handler;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.rabbitmq.service.IConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  

@Component("messageHandler")
public class MessageHandler {  
	
	private Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

	@Autowired(required=true)
	private List<IConsumerService> consumerServiceList = null;

	public void handleMessage(Message message) {  
		try{
			MessageProperties properties = message.getMessageProperties();
			String routingKey = properties.getReceivedRoutingKey();
			System.out.println("routingKey: " + routingKey);
			if (StringUtils.isBlank(routingKey)) return;
			for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
				consumerServiceList.get(i).handleMessage(routingKey, message.getBody());
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}  
	}  

} 


