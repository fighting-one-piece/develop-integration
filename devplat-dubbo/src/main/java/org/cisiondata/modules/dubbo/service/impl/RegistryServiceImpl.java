package org.cisiondata.modules.dubbo.service.impl;

import org.cisiondata.modules.dubbo.service.IRegistryService;

public class RegistryServiceImpl implements IRegistryService {

	@Override
	public void registry(String name) {
		System.out.println("service " + name + " registry success!");
	}

	
	
}
