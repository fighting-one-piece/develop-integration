package org.cisiondata.modules.dubbo.service.impl;

import org.cisiondata.modules.dubbo.service.IRegistryService;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "1.0.0", interfaceClass = IRegistryService.class, timeout = 10000)
public class RegistryServiceImpl implements IRegistryService {

	@Override
	public void registry(String name) {
		System.out.println("service " + name + " registry success!");
	}

	
	
}
