package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.AuthUserResource;
import org.springframework.stereotype.Repository;

@Repository("authUserResourceDAO")
public interface UserResourceDAO extends GenericDAO<AuthUserResource, Long>{

}
