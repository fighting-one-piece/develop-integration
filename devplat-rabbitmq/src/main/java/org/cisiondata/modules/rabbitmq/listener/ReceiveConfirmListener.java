package org.cisiondata.modules.rabbitmq.listener;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.rabbitmq.service.IConsumerService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;  

@Component("receiveConfirmListener")
public class ReceiveConfirmListener implements ChannelAwareMessageListener {
	
	@Autowired(required=true)
	private List<IConsumerService> consumerServiceList = null;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			System.out.println("receive confirm--:" + message.getMessageProperties());
			System.out.println("receive confirm--:" + SerializerUtils.read(message.getBody()));
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			MessageProperties properties = message.getMessageProperties();
			String routingKey = properties.getReceivedRoutingKey();
			System.out.println("routingKey: " + routingKey);
			if (StringUtils.isBlank(routingKey)) return;
			for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
				consumerServiceList.get(i).handleMessage(routingKey, 
						SerializerUtils.read(message.getBody()));
			}
		} catch (Exception e) {
			e.printStackTrace();// TODO 业务处理
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}
	}

}
