package org.cisiondata.modules.system.dao;

import java.util.List;

import org.cisiondata.modules.system.entity.IdCardAddress;
import org.springframework.stereotype.Repository;

@Repository("idCardAddressDAO")
public interface IdCardAddressDAO {

	/**
	 * 根据身份证前六位查询地区
	 * @param idCard
	 * @return
	 */
	public String findByIdCard(String idCard);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<IdCardAddress> findAll();
	
}
