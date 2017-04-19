package org.cisiondata.modules.elasticsearch.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.cisiondata.modules.elasticsearch.dao.LMDataDAO;
import org.cisiondata.modules.elasticsearch.entity.LMData;
import org.cisiondata.modules.elasticsearch.service.ILMDataService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("lmDataService")
public class LMDataServiceImpl implements ILMDataService{

	

	@Resource(name="lmDataDAO")
	private LMDataDAO lmDataDAO;
	
	@Override
	public void insetData(String lmKey,String queryData) throws BusinessException {
		LMData record = new LMData();
		record.setLmKey(lmKey);
		record.setQueryData(queryData);
		Date time = new Date();
		record.setQueryTime(time);
		lmDataDAO.insert(record);
	}

}
