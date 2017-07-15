package org.cisiondata.modules.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;  

@Component("confirmCallBackListener")
public class ConfirmCallBackListener  implements ConfirmCallback {  
	
	private Logger LOG = LoggerFactory.getLogger(ConfirmCallBackListener.class);
	
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
    	LOG.info("--confirm listener-- correlationData:{} ack:{} cause:{} ", correlationData , ack, cause);  
    }  

}
