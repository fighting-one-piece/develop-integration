package org.cisiondata.modules.auth.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.Resource;
import org.springframework.stereotype.Repository;

@Repository("resourceDAO")
public interface ResourceDAO extends GenericDAO<Resource, Long> {
	
	public List<Resource> findByType(int type);
	
}
