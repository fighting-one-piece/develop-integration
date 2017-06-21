package org.cisiondata.modules.rabbitmq.entity;

public enum CQueue {
	
	DEFAULT_QUEUE("defaultQueue", "default"),
	DEFAULT_TOPIC_QUEUE("defaultTopicQueue", "topic.default"),
	REQUEST_ACCESS_QUEUE("requestAccessQueue", "requestAccess"),
	RESPONSE_RESULT_QUEUE("responseResultQueue", "responseResult");
	
	private String name = null;
	
	private String routingKey = null;
	
	private CQueue(String name, String routingKey) {
		this.name = name;
		this.routingKey = routingKey;
	}

	public String getName() {
		return name;
	}

	public String getRoutingKey() {
		return routingKey;
	}


}
