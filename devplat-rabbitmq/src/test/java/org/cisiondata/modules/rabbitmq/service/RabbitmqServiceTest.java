package org.cisiondata.modules.rabbitmq.service;

import org.cisiondata.modules.rabbitmq.entity.CQueue;
import org.cisiondata.utils.serde.SerializerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext*.xml"})
public class RabbitmqServiceTest {

	@Autowired
	private IRabbitmqService rabbitmqService = null;
	
    private static String exChange = "directExchange";  
	
	@Test
	public void test01() throws InterruptedException {
		rabbitmqService.sendMessage(CQueue.DEFAULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is default message !!!!!!"));
		Thread.sleep(2000);
    	rabbitmqService.sendMessage(CQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is request access message !!!!!!"));
    	Thread.sleep(2000);
//    	rabbitmqService.sendMessage(MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey(), 
//    			SerializerUtils.write("this is response result message !!!!!!"));
//    	Thread.sleep(2000);
	}
	
	@Test
	public void test02() throws InterruptedException {
		rabbitmqService.sendMessage("topicExchange", "topic.default", 
    			SerializerUtils.write("this is topic message !!!!!!"));
    	rabbitmqService.sendTopicMessage("topic.sync", 
    			SerializerUtils.write("this is topic sync message !!!!!!"));
    	rabbitmqService.sendTopicMessage("topic.async", 
    			SerializerUtils.write("this is topic async message !!!!!!"));
		Thread.sleep(2000);
	}
	
	@Test
	public void test03() throws InterruptedException {
		rabbitmqService.sendMessage(exChange, CQueue.DEFAULT_QUEUE.getRoutingKey(), "aaa"); 
		Thread.sleep(1000);
		rabbitmqService.sendMessage(exChange, CQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), "bbb"); 
//		mqService.sendMessage(exChange, MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey(), "ccc"); 
    	Thread.sleep(1000);
	}
	
	@Test
	public void test04() throws InterruptedException {
		rabbitmqService.sendTopicMessage(CQueue.DEFAULT_TOPIC_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is default topic message !!!!!!"));
	}
	
	@Test
	public void test05() throws InterruptedException {
		DirectExchange exchange = new DirectExchange("c-exchange");
		rabbitmqService.declareExchange(exchange);
		Queue queue = new Queue("c-queue", true);
		rabbitmqService.declareQueue(queue);
		Binding binding = BindingBuilder.bind(queue).to(exchange).with("c-routingkey");
		rabbitmqService.declareBinding(binding);
		rabbitmqService.sendMessage("c-exchange", "c-routingkey", "this is a custom message!");
	}
	
	@Test    
    public void test1() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test1---message:"+message);  
        //exchange,queue 都正确, confirm被回调, ack=true  
        rabbitmqService.sendMessage(exChange, CQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test2() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test2---message:"+message);  
        //exchange 错误,queue 正确, confirm被回调, ack=false  
        rabbitmqService.sendMessage(exChange+"NO", CQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test3() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test3---message:"+message);  
        //exchange 正确,queue 错误 , confirm被回调, ack=true; return被回调 replyText:NO_ROUTE  
        rabbitmqService.sendMessage(exChange, "", message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test4() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test4---message:"+message);  
        //exchange 错误,queue 错误, confirm被回调, ack=false  
        rabbitmqService.sendMessage(exChange+"NO", CQueue.REQUEST_ACCESS_QUEUE.getRoutingKey() + "NO", message);    
        Thread.sleep(1000);  
    }    
	
}
