package org.cisiondata.modules.identity.dao;

import java.util.List;

import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.springframework.stereotype.Repository;
@Repository("attributiondao")
public interface MobileAttributionDao {
	public List<MobileAttributionModel> selByDnseg(String phone);
}
