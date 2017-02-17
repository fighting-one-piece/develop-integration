package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.Permission;
import org.springframework.stereotype.Repository;

@Repository("permissionDAO")
public interface PermissionDAO extends GenericDAO<Permission, Long> {

}
