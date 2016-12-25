package org.cisiondata.modules.authentication.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.authentication.entity.Permission;
import org.springframework.stereotype.Repository;

@Repository("permissionDAO")
public interface PermissionDAO extends GenericDAO<Permission, Long> {

}
