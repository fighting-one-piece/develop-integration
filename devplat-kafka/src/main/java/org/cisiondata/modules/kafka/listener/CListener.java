package org.cisiondata.modules.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.cisiondata.utils.serde.SerializerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

//@Component
public class CListener {
	
	private final Logger LOG = LoggerFactory.getLogger(CListener.class);
	
	@KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, byte[]> record) {
		LOG.info("kafka的key: " + record.key());
		LOG.info("kafka的value: " + SerializerUtils.read(record.value()));
    }

}
