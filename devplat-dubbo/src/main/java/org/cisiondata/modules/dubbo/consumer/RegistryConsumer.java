package org.cisiondata.modules.dubbo.consumer;

import org.cisiondata.modules.dubbo.service.IRegistryService;

import com.alibaba.dubbo.config.annotation.Reference;

public class RegistryConsumer {

	@Reference(version = "1.0.0", interfaceClass = IRegistryService.class)
	private IRegistryService registryService = null;
	
}
