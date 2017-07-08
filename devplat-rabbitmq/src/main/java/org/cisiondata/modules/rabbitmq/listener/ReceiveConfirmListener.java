package org.cisiondata.modules.rabbitmq.listener;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.rabbitmq.service.IConsumeService;
import org.cisiondata.utils.serde.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;  

@Component("receiveConfirmListener")
public class ReceiveConfirmListener implements ChannelAwareMessageListener {
	
	private Logger LOG = LoggerFactory.getLogger(ReceiveConfirmListener.class);
	
	@Autowired(required=true)
	private List<IConsumeService> consumerServiceList = null;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			channel.basicQos(100);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			MessageProperties properties = message.getMessageProperties();
			LOG.info("receive confirm: {}", properties);
			String exchange = properties.getReceivedExchange();
			String routingKey = properties.getReceivedRoutingKey();
			LOG.info("receive exchange: {} routingKey: {} message: {} ", exchange, routingKey, SerializerUtils.read(message.getBody()));
			if (StringUtils.isBlank(exchange) || StringUtils.isBlank(routingKey)) return;
			for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
				consumerServiceList.get(i).handleMessage(exchange, routingKey, 
						SerializerUtils.read(message.getBody()));
			}
		} catch (Exception e) {
			e.printStackTrace();// TODO 业务处理
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}
	}

}
