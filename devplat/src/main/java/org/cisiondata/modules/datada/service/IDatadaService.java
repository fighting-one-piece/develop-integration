package org.cisiondata.modules.datada.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.utils.exception.BusinessException;

public interface IDatadaService {

	/**
	 * 查询Datada数据
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String, Object>> readDatadaDatas(String query) throws BusinessException;
	
	/**
	 * 查询Datada数据
	 * @param query
	 * @param dateline
	 * @param searchToken
	 * @return
	 * @throws BusinessException
	 */
	public QueryResult<Map<String, Object>> readDatadaDatas(String query, String dateline, 
			String searchToken) throws BusinessException;
	
	//查询手机号码是否在集群中
	public boolean readPhoneIsExists(String phone) throws BusinessException;
	
	//查询身份号码是否在集群中
	public boolean readIdCardIsExists(String idCard)throws BusinessException;
}
