package org.cisiondata.modules.rabbitmq.listener;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ReturnListener;

@Service("customRetrunListener")
public class CustomRetrunListener implements ReturnListener {

	@Override
	public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
			BasicProperties properties, byte[] body) throws IOException {
		System.out.println("replyCode: " + replyCode);
		System.out.println("replyText: " + replyText);
		System.out.println("exchange: " + exchange);
		System.out.println("routingKey: " + routingKey);
		System.out.println("properties: " + properties);
		System.out.println("body: " + body);
	}

}
