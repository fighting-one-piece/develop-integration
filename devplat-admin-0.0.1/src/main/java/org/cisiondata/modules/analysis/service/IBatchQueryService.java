package org.cisiondata.modules.analysis.service;

import java.util.List;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.cisiondata.utils.exception.BusinessException;

public interface IBatchQueryService extends IGenericService<EventExtend, Long>{
	
	public List<String> updateStatistics(String type,String fileName) throws BusinessException;
	
	public List<String> Classifi(String Classification,String index,String type,String filename)throws BusinessException;
	
}
