package org.cisiondata.utils.ds;

import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
	}
	
	@Override
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		String currentLookupKey = DataSourceContextHolder.getDataSource();
		if (currentLookupKey.startsWith("slave")) {
			currentLookupKey = "slave1";
		}
		return currentLookupKey;
	}

}
