package org.cisiondata.modules.identity.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.identity.dao.MobileAttributionDAO;
import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.cisiondata.modules.identity.service.IMobileAttributionService;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.stereotype.Service;
@Service("mobileAttributionService")
public class MobileAttributionServiceImpl implements IMobileAttributionService{
	
	private static final String PHONE_REX = "1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}";
	
	
	@Resource(name = "mobileAttributionDAO")
	private MobileAttributionDAO mobileAttributionDAO = null;
	
	
	public String readAttributionByMobile(String mobile){
		StringBuffer code = new StringBuffer();
		try {
			if(mobile == null) return null;
			mobile = mobile.replaceAll("<span style=\"color:red\">", "").replaceAll("</span>", "").trim();
			if (StringUtils.isBlank(mobile)) return null;
			if(!mobile.matches(PHONE_REX)) return null;
			//截取字符串
			String mobilePrefix = mobile.substring(0, 7);
			Object attribution = RedisClusterUtils.getInstance().get("mobile:attribution:"+mobilePrefix);
			if (null == attribution){
				MobileAttributionModel mobiles = mobileAttributionDAO.selByDnsegs(mobilePrefix);
				if(mobiles.getDnseg() == null) return null;
				if(mobiles.getProvince().equals("北京市") || mobiles.getProvince().equals("天津市") || mobiles.getProvince().equals("重庆") || mobiles.getProvince().equals("上海市")){
					code.append(mobiles.getCity());
					code.append(" "+mobiles.getOperator());
					RedisClusterUtils.getInstance().set("mobile:attribution:" + mobiles.getDnseg(), code.toString());
				}else{
					code.append(mobiles.getProvince()+" ");
					code.append(mobiles.getCity()+" ");
					code.append(mobiles.getOperator());
					RedisClusterUtils.getInstance().set("mobile:attribution:" + mobiles.getDnseg(), code.toString());
				}
			}
			return code.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
