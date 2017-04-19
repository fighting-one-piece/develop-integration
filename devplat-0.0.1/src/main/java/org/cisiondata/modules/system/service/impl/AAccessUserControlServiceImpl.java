package org.cisiondata.modules.system.service.impl;

import javax.annotation.Resource;

import org.cisiondata.modules.system.dao.AAccessUserControlDao;
import org.cisiondata.modules.system.entity.AAccessUserControl;
import org.cisiondata.modules.system.service.IAAccessUserControlService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("baccessUserControlService")
public class AAccessUserControlServiceImpl implements IAAccessUserControlService {

	@Resource(name = "baccessUserControlDao")
	private AAccessUserControlDao baccessUserControlDao = null;

	@Override
	public Long selectQueryTimes(String account) throws BusinessException {
		return baccessUserControlDao.findAccessControlRemainingCount(account);
		
	}

	@Override
	public void updateQueryTimes(AAccessUserControl accessUserControl) throws BusinessException {
		baccessUserControlDao.updateAccessControlByAccount(accessUserControl);
		
	}
	
	

}
