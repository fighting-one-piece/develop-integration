package org.cisiondata.modules.identity.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;

/**
 * 实现接口
 * @author Administrator
 *
 */
public interface IMobileUsersService {

	public  List<Map<String, Object>> readphoneNUmbers(String phone) throws BusinessException;
}
