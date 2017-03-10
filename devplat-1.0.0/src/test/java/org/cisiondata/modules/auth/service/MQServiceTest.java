package org.cisiondata.modules.auth.service;

import javax.annotation.Resource;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.modules.rabbitmq.service.IMQService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class MQServiceTest {

	@Resource(name = "mqService")
	private IMQService mqService = null;
	
	@Resource(name = "redisMQService")
	private IMQService rmqService = null;
	
	@Test
	public void testSendMessage01() {
		mqService.sendMessage(MQueue.DEFAULT_QUEUE.getName(), "this is default message");
	}
	
	@Test
	public void testSendMessage02() {
		rmqService.sendMessage(MQueue.DEFAULT_QUEUE.getName(), "this is default message");
	}
	
	@Test
	public void testReceiveMessage02() {
		Object message = rmqService.receiveMessage(MQueue.DEFAULT_QUEUE.getName());
		System.out.println("message: " + message);
	}
	
	@Test
	public void test() {
    	mqService.sendMessage(MQueue.DEFAULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is default message"));
    	mqService.sendMessage(MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is request access message"));
    	mqService.sendMessage(MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message"));
    	mqService.sendTopicMessage(MQueue.DEFAULT_TOPIC_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message"));
	}
	
	
}
