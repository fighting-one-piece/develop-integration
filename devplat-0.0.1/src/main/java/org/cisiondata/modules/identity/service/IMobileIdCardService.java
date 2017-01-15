package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

/**
 * 分类查询接口
 * @author Administrator
 *
 */
public interface IMobileIdCardService {
	
	/**
	 * 根据手机号码或身份证号读取统计信息和标签信息
	 * @param mobileOrIdCard
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> readStatisticsAndLabels(String identity) throws BusinessException;
	
	//接收接口
	public List<Map<String, Object>> readClassifiedQuery(String index, String type, String identity) throws BusinessException;
}
