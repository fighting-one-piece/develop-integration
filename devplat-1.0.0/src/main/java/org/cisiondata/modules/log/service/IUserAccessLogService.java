package org.cisiondata.modules.log.service;

import java.util.Date;
import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IUserAccessLogService {
	//时间段查询
	public Map<String,Object> selAccessLog(String params,Date startTime,Date endTime,int index,int pageSize) throws BusinessException;
}
