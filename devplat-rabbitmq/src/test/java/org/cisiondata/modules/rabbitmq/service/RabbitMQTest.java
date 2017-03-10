package org.cisiondata.modules.rabbitmq.service;

import org.cisiondata.modules.rabbitmq.entity.MQueue;
import org.cisiondata.utils.serde.SerializerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext*.xml"})
public class RabbitMQTest {

	@Autowired
	private IMQService mqService = null;
	
    private static String exChange = "directExChange";  
	
	@Test
	public void test01() throws InterruptedException {
		mqService.sendMessage(MQueue.DEFAULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is default message !!!!!!"));
		Thread.sleep(2000);
    	mqService.sendMessage(MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is request access message !!!!!!"));
    	Thread.sleep(2000);
    	mqService.sendMessage(MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message !!!!!!"));
    	Thread.sleep(2000);
    	mqService.sendTopicMessage(MQueue.DEFAULT_TOPIC_QUEUE.getRoutingKey(), 
    			SerializerUtils.write("this is response result message !!!!!!"));
    	Thread.sleep(2000);
	}
	
	@Test
	public void test02() throws InterruptedException {
    	for (int i = 0; i < 15; i++) {
    		mqService.sendMessage(SerializerUtils.write(String.format("this is %s message!!!!!!", i)));
    		Thread.sleep(1000);
    	}
    	Thread.sleep(1000);
	}
	
	@Test
	public void test03() throws InterruptedException {
		mqService.sendMessage(exChange, MQueue.DEFAULT_QUEUE.getRoutingKey(), "aaa"); 
		Thread.sleep(1000);
		mqService.sendMessage(exChange, MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), "bbb"); 
//		mqService.sendMessage(exChange, MQueue.RESPONSE_RESULT_QUEUE.getRoutingKey(), "ccc"); 
    	Thread.sleep(1000);
	}
	
	@Test    
    public void test1() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test1---message:"+message);  
        //exchange,queue 都正确, confirm被回调, ack=true  
        mqService.sendMessage(exChange, MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test2() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test2---message:"+message);  
        //exchange 错误,queue 正确, confirm被回调, ack=false  
        mqService.sendMessage(exChange+"NO", MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test3() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test3---message:"+message);  
        //exchange 正确,queue 错误 , confirm被回调, ack=true; return被回调 replyText:NO_ROUTE  
        mqService.sendMessage(exChange, "", message);    
        Thread.sleep(1000);  
    }    
      
    @Test    
    public void test4() throws InterruptedException{    
        String message = "currentTime:"+System.currentTimeMillis();  
        System.out.println("test4---message:"+message);  
        //exchange 错误,queue 错误, confirm被回调, ack=false  
        mqService.sendMessage(exChange+"NO", MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey() + "NO", message);    
        Thread.sleep(1000);  
    }    
	
}
