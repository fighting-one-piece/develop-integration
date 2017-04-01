package org.cisiondata.modules.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.utils.exception.BusinessException;

public interface IResourceService {

	/**
	 * 根据url和账号获取字段处理方式
	 * @return
	 * @throws BusinessException
	 */
	public List<ResourceInterfaceField> findAttributeByUrl(HttpServletRequest req) throws BusinessException;
	
}
