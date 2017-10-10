package org.cisiondata.modules.websocket.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.utils.serde.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

@Component("messagePushHandler")
public class MessagePushHandler implements WebSocketHandler {

	private Logger LOG = LoggerFactory.getLogger(MessagePushHandler.class);
	
	@Resource(name = "connectionFactory")
	private ConnectionFactory connectionFactory = null;

	private static final Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("session connection establish !!!");
		
		Map<String, Object> attributes = session.getAttributes();
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		Object accountObj = attributes.get("account");
		if (null != accountObj) {
			sessions.put((String) accountObj, session);
		}

		session.sendMessage(new TextMessage("you connect server success"));
		session.sendMessage(new TextMessage("you can receive message"));
		
		Connection connection = connectionFactory.createConnection();
		Channel channel = connection.createChannel(false);
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        channel.basicConsume("pushQueue", true, consumer);  
        while (true) {  
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody()); 
            SerializerUtils.read(delivery.getBody());
            System.out.println("Received '" + message + "'");  
        }  
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOG.info("handle message: {}", message.getPayload());
		// sendMessageToUsers();
		session.sendMessage(new TextMessage(new Date() + ""));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) session.close();
//		users.remove(session);

		LOG.error("handle transport error, " + exception.getMessage(), exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getAttributes().get(""));
		LOG.info("after connection closed {}", closeStatus.getReason());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession session : sessions.values()) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
