package org.cisiondata.modules.rabbitmq.service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

@SuppressWarnings("deprecation")
public class RabbitmqApiTest {
	
	private static final String EXCHANGE_NAME = "topic_logs";  

	public void producer() throws IOException, TimeoutException {
		// 创建连接和频道  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
  
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");  
  
        String[] routing_keys = new String[] { "kernal.info", "cron.warning",  
                "auth.info", "kernel.critical" };  
        for (String routing_key : routing_keys) {  
            String msg = UUID.randomUUID().toString();  
            channel.basicPublish(EXCHANGE_NAME, routing_key, null, msg  
                    .getBytes());  
            System.out.println(" [x] Sent routingKey = "+routing_key+" ,msg = " + msg + ".");  
        }  
        channel.close();  
        connection.close();  
	}
	
	public void consumer01() throws Exception {
		 // 创建连接和频道  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        // 声明转发器  
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");  
        // 随机生成一个队列  
        String queueName = channel.queueDeclare().getQueue();  
          
        //接收所有与kernel相关的消息  
        channel.queueBind(queueName, EXCHANGE_NAME, "kernel.*");  
  
        System.out.println(" [*] Waiting for messages about kernel. To exit press CTRL+C");  
  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        channel.basicConsume(queueName, true, consumer);  
  
        while (true) {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            String routingKey = delivery.getEnvelope().getRoutingKey();  
            System.out.println(" [x] Received routingKey = " + routingKey  
                    + ",msg = " + message + ".");  
        }  
	}
	
}
