package org.cisiondata.modules.dubbo.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
	
	@SuppressWarnings("resource")
	@Test
	public void testSubscriber() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-service-subscriber.xml");
		context.start();  
		IRegistryService registryService = (IRegistryService) context.getBean("registryService");
		registryService.registry("world");
	}

}
