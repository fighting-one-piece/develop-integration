package org.cisiondata.modules.main;

import org.cisiondata.modules.cache.service.ICacheService;
import org.cisiondata.modules.cache.service.impl.CacheServiceImpl;

public class MainApplication {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("input arguments must be two!");
		}
		Object returnResult = null;
		ICacheService cacheService = new CacheServiceImpl();
		switch(args[0]) {
			case "key" : returnResult = cacheService.readKeys(args[1]); break;
			case "value" : returnResult = cacheService.readValueByKey(args[1]) ; break;
			case "values" : returnResult = cacheService.readValuesByKey(args[1]) ; break;
			case "valueByNotSerKey" : returnResult = cacheService.readValueByNotSerKey(args[1]) ; break;
			case "delete" : returnResult = cacheService.deleteKeys(args[1]); break;
			case "deleteNotSerKey" : returnResult = cacheService.deleteNotSerKey(args[1]); break;
			default: break;
		}
		System.out.println("returnResult: " + returnResult);
	}

}
