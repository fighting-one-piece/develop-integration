package org.cisiondata.modules.rabbitmq.listener;

import org.cisiondata.utils.serde.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;  

@Component("returnCallBackListener")  
public class ReturnCallBackListener implements ReturnCallback { 
	
	private Logger LOG = LoggerFactory.getLogger(ReturnCallBackListener.class);
	
    @Override  
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) { 
    	LOG.info("--return callback-- exchange:{} routingKey:{} replyCode:{} replyText:{} message:{}", 
    			exchange, routingKey, replyCode, replyText, SerializerUtils.read(message.getBody()));
    }  

}
