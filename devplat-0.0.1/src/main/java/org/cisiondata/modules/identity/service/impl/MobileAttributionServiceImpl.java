package org.cisiondata.modules.identity.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.identity.dao.MobileAttributionDAO;
import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.cisiondata.modules.identity.service.IMobileAttributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisCluster;

@Service("mobileAttributionService")
public class MobileAttributionServiceImpl implements IMobileAttributionService, InitializingBean {
	
	private Logger LOG = LoggerFactory.getLogger(MobileAttributionServiceImpl.class);
	
	private static final String PHONE_REX = "1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}";
	
	private static final String CACHE_KEY = "mobile:attributions";
	
	@Resource(name = "mobileAttributionDAO")
	private MobileAttributionDAO mobileAttributionDAO = null;
	
	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final List<MobileAttributionModel> attributions = mobileAttributionDAO.selAll();
		long hlen = jedisCluster.hlen(CACHE_KEY);
		if (hlen != attributions.size()) {
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					for (int i = 0, len = attributions.size(); i < len; i++) {
						MobileAttributionModel attribution = attributions.get(i);
						if(attribution.getDnseg() == null) continue;
						StringBuilder sb = new StringBuilder();
						if(attribution.getProvince().equals("北京市") || attribution.getProvince().equals("天津市") || attribution.getProvince().equals("重庆") || attribution.getProvince().equals("上海市")){
							sb.append(attribution.getCity());
						} else {
							sb.append(attribution.getProvince()+" ");
							sb.append(attribution.getCity()+" ");
						}
						sb.append(attribution.getOperator());
						jedisCluster.hset(CACHE_KEY, attribution.getDnseg(), sb.toString());
					}
					LOG.info("Mobile Attribution Initialize Data Finish!");
				}
			});
		}
		LOG.info("Mobile Attribution Initialize Data Finish!");
	}
	
	public String readAttributionByMobile(String mobile){
		try {
			if(mobile == null) return null;
			mobile = mobile.replaceAll("<span style=\"color:red\">", "").replaceAll("</span>", "").trim();
			if (StringUtils.isBlank(mobile)) return null;
			if(!mobile.matches(PHONE_REX)) return null;
			//截取字符串
			String mobilePrefix = mobile.substring(0, 7);
//			Object attribution = RedisClusterUtils.getInstance().get("mobile:attribution:" + mobilePrefix);
			Object attribution = jedisCluster.hget(CACHE_KEY, mobilePrefix);
			if (null == attribution){
				MobileAttributionModel model = mobileAttributionDAO.selByDnsegs(mobilePrefix);
				if(model.getDnseg() == null) return null;
				StringBuilder sb = new StringBuilder();
				if(model.getProvince().equals("北京市") || model.getProvince().equals("天津市") || 
						model.getProvince().equals("重庆") || model.getProvince().equals("上海市")){
					sb.append(model.getCity());
				} else {
					sb.append(model.getProvince()+" ");
					sb.append(model.getCity()+" ");
				}
				sb.append(model.getOperator());
//				RedisClusterUtils.getInstance().set("mobile:attribution:" + mobilePrefix, code.toString());
				jedisCluster.hset(CACHE_KEY, mobilePrefix, sb.toString());
				return sb.toString();
			} else {
				return String.valueOf(attribution);
			}
		} catch (Exception e) {
			return null;
		}
	}

}
