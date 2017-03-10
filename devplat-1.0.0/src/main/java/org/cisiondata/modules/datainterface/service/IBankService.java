package org.cisiondata.modules.datainterface.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

public interface IBankService {
	//根据手机号查询银行卡信息
	public Map<String, Object> readBankPhone(String phone)throws BusinessException;
	// 银行卡消费信息查询
	public Map<String, String> readqueryQuota1(String bankCard) throws BusinessException;

}
