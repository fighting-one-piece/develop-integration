package org.cisiondata.modules.rabbitmq.example;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class FProducer {

	@Resource(name="rAmqpTemplate")
    private AmqpTemplate amqpTemplate = null;

    public void sendMessage(Object message){
    	System.out.println("rproducer send message: " + message);
        amqpTemplate.convertAndSend("default", message);
    }
	
}
