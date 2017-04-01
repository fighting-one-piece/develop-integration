package org.cisiondata.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.system.dao.IdCardAddressDAO;
import org.cisiondata.modules.system.entity.IdCardAddress;
import org.cisiondata.modules.system.service.IIdCardAddressService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("idCardAddressService")
public class IdCardAddressServiceImpl implements IIdCardAddressService,InitializingBean {

	private static final String ID_CARD_REX = "\\d{17}(\\d|X|x)|\\d{15}";
	
	private Map<String,String> codeMap = new HashMap<String,String>();
	
	@Resource(name = "idCardAddressDAO")
	private IdCardAddressDAO idCardAddressDAO;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<IdCardAddress> list = idCardAddressDAO.findAll();
		for (IdCardAddress idCardAddress : list) {
			codeMap.put(idCardAddress.getIdCard(), idCardAddress.getAddress());
		}
	}
	
	@Override
	public String readAttributionByIdCard(String idCard) {
		try {
			if(null == idCard) return null;
			idCard = idCard.replaceAll("<span style=\"color:red\">", "").replaceAll("</span>", "").trim();
			if (StringUtils.isBlank(idCard)) return null;
			if (!idCard.matches(ID_CARD_REX)) return null;
			String pre = idCard.substring(0, 6);
			if(codeMap.containsKey(pre)) return codeMap.get(pre);
			return null;
		} catch (Exception e) {
			return null;
		}
	}


}
