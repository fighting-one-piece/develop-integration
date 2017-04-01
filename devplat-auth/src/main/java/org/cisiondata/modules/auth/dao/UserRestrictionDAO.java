package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.UserRestriction;
import org.springframework.stereotype.Repository;

@Repository("userRestrictionDAO")
public interface UserRestrictionDAO extends GenericDAO<UserRestriction, Long> {
	
}
