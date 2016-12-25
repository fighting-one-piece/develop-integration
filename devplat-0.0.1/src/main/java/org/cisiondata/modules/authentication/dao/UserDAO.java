package org.cisiondata.modules.authentication.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.authentication.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public interface UserDAO extends GenericDAO<User, Long> {

}
