package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.cisiondata.utils.exception.BusinessException;

public interface IMobileService {
	
	/**
	 * 读取手机信息
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> readMobileInfo(String mobile) throws BusinessException;

	/**
	 * 读取手机信息
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> readMobileDynamicInfo(String mobile) throws BusinessException;
	/**
	 * 手机归属地
	 * @param phone
	 * @return
	 */
	public List<MobileAttributionModel> selByDnseg(String phone);
	
}
