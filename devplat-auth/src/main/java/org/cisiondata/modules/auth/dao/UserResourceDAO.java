package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.UserResource;
import org.springframework.stereotype.Repository;

@Repository("userResourceDAO")
public interface UserResourceDAO extends GenericDAO<UserResource, Long>{

}
