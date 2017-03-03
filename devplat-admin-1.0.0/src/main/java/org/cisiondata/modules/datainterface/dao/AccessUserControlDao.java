package org.cisiondata.modules.datainterface.dao;

import java.util.List;

import org.cisiondata.modules.datainterface.entity.AccessUserControl;
import org.springframework.stereotype.Repository;

@Repository("aaccessUserControlDao")
public interface AccessUserControlDao {

	public int updateAccessControlByAccount(AccessUserControl accessUserControl);
	
	public int updateMoneyByAccount(AccessUserControl accessUserControl);
	
	public AccessUserControl findAccessControlByAccount(String account);
	
	public int addAccessUserControl(AccessUserControl accessUserControl);
	
	public  List<String> findAccessControlAll();
	/**
	 * 根据source查询账号
	 * @param source
	 * @return
	 */
	public List<String> findAccountBySource(Integer source);
}
