package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthRoleResource;
import org.springframework.stereotype.Repository;

@Repository("authRoleResourceDAO")
public interface RoleResourceDAO extends GenericDAO<AuthRoleResource, Long>{

}
