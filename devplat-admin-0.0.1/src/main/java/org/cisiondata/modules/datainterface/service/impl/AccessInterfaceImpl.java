package org.cisiondata.modules.datainterface.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessInterfaceDao;
import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.modules.datainterface.service.IAccessInterfaceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("accessInterfaceService")
public class AccessInterfaceImpl implements IAccessInterfaceService {
	
	@Resource(name = "accessInterfaceDao")
	private AccessInterfaceDao accessInterfaceDao;

	@Override
	public Map<String, Object> findAccessInterfaceByPage(int page, int pageSize) throws BusinessException {
		int count = accessInterfaceDao.findCount();
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		List<AccessInterface> accessList = accessInterfaceDao.findByPage(begin, pageSize);
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("data", accessList);
		map.put("pageNum", page);
		map.put("pageCount", pageCount);
		return map;
	}

	@Override
	public int updateDeleteFlag(AccessInterface accessInterface) throws BusinessException {
		return accessInterfaceDao.updateAccessInterfaceDeleteFlag(accessInterface);
	}

	@Override
	public int addAccessInterface(AccessInterface accessInterface) throws BusinessException {
		return accessInterfaceDao.addAccessInterface(accessInterface);
	}

	@Override
	public int updateAccessInterface(AccessInterface accessInterface) throws BusinessException {
		return accessInterfaceDao.updateAccessInterface(accessInterface);
	}

	
}
