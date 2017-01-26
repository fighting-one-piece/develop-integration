package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.UserOnline;
import org.springframework.stereotype.Repository;

@Repository("userOnlineDAO")
public interface UserOnlineDAO extends GenericDAO<UserOnline, Long> {

	
}
