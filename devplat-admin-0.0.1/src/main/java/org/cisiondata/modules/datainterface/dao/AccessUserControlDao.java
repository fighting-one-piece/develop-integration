package org.cisiondata.modules.datainterface.dao;

import org.cisiondata.modules.datainterface.entity.AccessUserControl;
import org.springframework.stereotype.Repository;

@Repository("aaccessUserControlDao")
public interface AccessUserControlDao {

	public int updateAccessControlByAccount(AccessUserControl accessUserControl);
	
	public AccessUserControl findAccessControlByAccount(String account);
	
	public int addAccessUserControl(AccessUserControl accessUserControl);
}
