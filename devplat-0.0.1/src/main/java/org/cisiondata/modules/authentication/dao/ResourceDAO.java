package org.cisiondata.modules.authentication.dao;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.authentication.entity.Resource;
import org.springframework.stereotype.Repository;

@Repository("resourceDAO")
public interface ResourceDAO extends GenericDAO<Resource, Long> {

}
