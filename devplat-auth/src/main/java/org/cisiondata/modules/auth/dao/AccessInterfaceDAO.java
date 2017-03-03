package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AccessInterface;
import org.springframework.stereotype.Repository;

@Repository("accessInterfaceDAO")
public interface AccessInterfaceDAO extends GenericDAO<AccessInterface, Long> {

}
