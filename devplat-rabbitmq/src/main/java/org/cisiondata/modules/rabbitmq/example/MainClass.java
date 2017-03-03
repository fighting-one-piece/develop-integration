package org.cisiondata.modules.rabbitmq.example;

import org.cisiondata.modules.rabbitmq.service.IMQService;
import org.cisiondata.modules.rabbitmq.service.Queue;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	
    @SuppressWarnings("resource")
	public static void main( String[] args ) {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
    	IMQService mqService = context.getBean(IMQService.class);
    	mqService.sendMessage(Queue.DEFAULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is default message"));
    	mqService.sendMessage(Queue.REQUEST_ACCESS_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is request access message"));
    	mqService.sendMessage(Queue.RESPONSE_RESULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message"));
    	mqService.sendTopicMessage(Queue.DEFAULT_TOPIC_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message"));
    	context.destroy();
    }
}
