package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthPermission;
import org.springframework.stereotype.Repository;

@Repository("authPermissionDAO")
public interface PermissionDAO extends GenericDAO<AuthPermission, Long> {

}
