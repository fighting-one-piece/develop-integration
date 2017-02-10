package org.cisiondata.modules.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.cisiondata.modules.auth.service.IShiroFilterChainService;
import org.springframework.stereotype.Service;

@Service("shiroFilterChainService")
public class ShiroFilterChainServiceImpl implements IShiroFilterChainService {

//	@Autowired
	private DefaultFilterChainManager filterChainManager = null;
	
	private Map<String, NamedFilterList> defaultFilterChains = null;
	
	@PostConstruct
	public void postConstruct() {
		defaultFilterChains = new HashMap<String, NamedFilterList>();
	}
	
	public void initFilterChains() {
		filterChainManager.getFilterChains().clear();
		if (null != defaultFilterChains) {
			filterChainManager.getFilterChains().putAll(defaultFilterChains);
		}
		
		
//		filterChainManager.addToChain(chainName, filterName, chainSpecificFilterConfig);
		
		
	}
	
}
