package org.cisiondata.modules.datainterface.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.springframework.stereotype.Repository;

@Repository("accessUserDAO")
public interface AccessUserDAO extends GenericDAO<AccessUser, Long> {

}
