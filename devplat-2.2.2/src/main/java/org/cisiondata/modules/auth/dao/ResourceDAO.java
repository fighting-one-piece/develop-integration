package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthResource;
import org.springframework.stereotype.Repository;

@Repository("authResourceDAO")
public interface ResourceDAO extends GenericDAO<AuthResource, Long> {
	
}
