package org.cisiondata.modules.identity.dao;

import java.util.List;

import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.springframework.stereotype.Repository;

@Repository("mobileAttributionDAO")
public interface MobileAttributionDAO {
	
	public List<MobileAttributionModel> selByDnseg(String phone);
	public MobileAttributionModel selByDnsegs(String phone);
	public List<MobileAttributionModel> selAll();
}
