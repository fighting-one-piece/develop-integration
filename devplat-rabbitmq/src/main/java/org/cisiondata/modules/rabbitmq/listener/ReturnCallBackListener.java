package org.cisiondata.modules.rabbitmq.listener;

import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;  

@Component("returnCallBackListener")  
public class ReturnCallBackListener implements ReturnCallback{  
	
    @Override  
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {  
        System.out.println("return--replyCode: " + replyCode + ", replyText: " + replyText);  
        System.out.println("return--exchange: " + exchange + ", routingKey: " + routingKey);  
        System.out.println("return--message: " + SerializerUtils.read(message.getBody()));  
    }  

}
