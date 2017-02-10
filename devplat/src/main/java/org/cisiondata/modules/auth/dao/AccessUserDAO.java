package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AccessUser;
import org.springframework.stereotype.Repository;

@Repository("accessUserDAO")
public interface AccessUserDAO extends GenericDAO<AccessUser, Long> {

}
