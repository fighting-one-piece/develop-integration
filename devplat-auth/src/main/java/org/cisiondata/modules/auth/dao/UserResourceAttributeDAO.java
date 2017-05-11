package org.cisiondata.modules.auth.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.auth.entity.UserResourceAttribute;
import org.springframework.stereotype.Repository;

@Repository("userResourceAttributeDAO")
public interface UserResourceAttributeDAO extends GenericDAO<UserResourceAttribute, Long> {

}
