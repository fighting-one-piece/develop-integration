package org.cisiondata.modules.analysis.service;

import java.util.List;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.cisiondata.utils.exception.BusinessException;

public interface IBatchQueryService extends IGenericService<EventExtend, Long>{
	
	public void updateStatistics(String type,String fileName) throws BusinessException;
	
	public List<String> Classifi(String Classification,String index,String type,String filename)throws BusinessException;
	
	public int hitRate(String filename);
	
	//全局匹配分析结果
	public List<String> result(String fileName);
}
