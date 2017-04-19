package org.cisiondata.modules.system.dao;


import org.cisiondata.modules.system.entity.AAccessUserControl;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("baccessUserControlDao")
public interface AAccessUserControlDao {
	//修改剩余条数
	public void updateAccessControlByAccount(AAccessUserControl accessUserControl) throws DataAccessException;
	
	//查询剩余条数
	public Long findAccessControlRemainingCount(String account) throws DataAccessException;
	
}
