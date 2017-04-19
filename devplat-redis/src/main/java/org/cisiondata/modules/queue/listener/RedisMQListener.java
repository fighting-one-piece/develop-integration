package org.cisiondata.modules.queue.listener;

import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.queue.service.IConsumerService;
import org.cisiondata.modules.queue.service.IRedisMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class RedisMQListener {

	@Resource(name = "redisMQService")
	private IRedisMQService redisMQService = null;
	
	@Autowired(required=true)
	private List<IConsumerService> consumerServiceList = null;
	
	@Scheduled(fixedRate = 2000, initialDelay = 60000)
	public void onMessage() {
		for (String routingKey : redisMQService.getRoutingKeys()) {
			long mqLength = redisMQService.readMessageQueueLength(routingKey);
			if (mqLength > 0){
				Object message = redisMQService.receiveMessage(routingKey);
				for (int i = 0, len = consumerServiceList.size(); i < len; i++) {
					for (int j = 0; j < mqLength; j++) {
						consumerServiceList.get(i).handleMessage(routingKey, message);
					}
				}
			}
		}
	}
	
}
