package org.cisiondata.modules.elasticsearch.service;

import org.cisiondata.utils.exception.BusinessException;

public interface ILMDataService {
	//插入数据库
	public void insetData(String lmKey,String queryData) throws BusinessException;
	
}
