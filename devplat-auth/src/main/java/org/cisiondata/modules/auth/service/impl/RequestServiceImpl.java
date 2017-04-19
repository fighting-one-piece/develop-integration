package org.cisiondata.modules.auth.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.auth.service.IRequestService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestServiceImpl implements IRequestService {

	protected Logger LOG = LoggerFactory.getLogger(RequestServiceImpl.class);
	
	@Override
	public Object[] preHandle(HttpServletRequest request) throws BusinessException {
		return new Object[]{true};
	}

	@Override
	public Object[] postHandle(HttpServletRequest request, Object result) throws BusinessException {
		return null;
	}

}
