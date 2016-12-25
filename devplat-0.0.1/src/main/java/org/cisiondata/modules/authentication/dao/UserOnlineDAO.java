package org.cisiondata.modules.authentication.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.authentication.entity.UserOnline;
import org.springframework.stereotype.Repository;

@Repository("userOnlineDAO")
public interface UserOnlineDAO extends GenericDAO<UserOnline, Long> {

	
}
