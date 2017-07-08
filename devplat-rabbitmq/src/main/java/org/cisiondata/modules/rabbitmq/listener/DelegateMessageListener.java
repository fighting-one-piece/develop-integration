package org.cisiondata.modules.rabbitmq.listener;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.rabbitmq.service.IConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;  

@Component("delegateMessageListener")
public class DelegateMessageListener {  
	
	private Logger LOG = LoggerFactory.getLogger(DelegateMessageListener.class);

	@Autowired(required=true)
	private List<IConsumeService> consumerServiceList = null;

	public void onMessage(Message message) {  
		try{
			MessageProperties properties = message.getMessageProperties();
			String exchange = properties.getReceivedExchange();
			String routingKey = properties.getReceivedRoutingKey();
			LOG.info("DelegateMessageListener routingKey: " + routingKey);
			if (StringUtils.isBlank(routingKey)) return;
			for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
				consumerServiceList.get(i).handleMessage(exchange, routingKey, message.getBody());
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}  
	}  

} 


