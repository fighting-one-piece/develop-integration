package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.user.entity.APermission;
import org.springframework.stereotype.Repository;

@Repository("aPermissionDao")
public interface APermissionDao extends GenericDAO<APermission, Long> {

	public List<APermission> findByPrincipalTypeAndPrincipalId(APermission permission);
	
	public APermission findPermission(APermission permission);
	
	public int deleteByPrincipalTypeAndPrincipalId(APermission permission);
	
	public int addPermission(APermission permission);
	
	public int updatePermission(APermission permission);
}
