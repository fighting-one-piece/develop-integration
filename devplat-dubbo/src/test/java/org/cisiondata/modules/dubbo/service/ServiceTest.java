package org.cisiondata.modules.dubbo.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
	
	@SuppressWarnings("resource")
	public static void testProvider() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-service-provider.xml");
		context.start();  
		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("resource")
	public static void testConsumer() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-service-consumer.xml");
		context.start();  
		IRegistryService registryService = (IRegistryService) context.getBean("registryService");
		registryService.registry("world");
	}
	
	public static void main(String[] args) {
		testProvider();
//		testConsumer();
	}

}
