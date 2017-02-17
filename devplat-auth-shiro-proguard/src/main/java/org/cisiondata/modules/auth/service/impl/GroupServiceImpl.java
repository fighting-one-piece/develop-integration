package org.cisiondata.modules.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.auth.dao.GroupDAO;
import org.cisiondata.modules.auth.entity.Group;
import org.cisiondata.modules.auth.service.IGroupService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("groupService")
public class GroupServiceImpl extends GenericServiceImpl<Group, Long> implements IGroupService {
	
	@Resource(name = "groupDAO")
	private GroupDAO groupDAO = null;

	@Override
	public GenericDAO<Group, Long> obtainDAOInstance() {
		return groupDAO;
	}
	
	@Override
	public List<Group> readGroupsByUserId(Long userId) throws BusinessException {
		return groupDAO.readDataListByUserId(userId);
	}
	
	@Override
	public Set<String> readGroupIdentitiesByUserId(Long userId) throws BusinessException {
		List<Group> groups = groupDAO.readDataListByUserId(userId);
		Set<String> identities = new HashSet<String>();
		for (int i = 0, len = groups.size(); i < len; i++) {
			identities.add(groups.get(i).getIdentity());
		}
		return identities;
	}
	

}
