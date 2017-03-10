package org.cisiondata.modules.identity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.cisiondata.modules.identity.dao.MobileAttributionDAO;
import org.cisiondata.modules.identity.entity.MobileAttributionModel;
import org.cisiondata.modules.identity.service.IMobileAddressService;
import org.cisiondata.modules.identity.service.IMobileNameService;
import org.cisiondata.modules.identity.service.IMobileService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mobileService")
public class MobileServiceImpl implements IMobileService {
	
	private Logger LOG = LoggerFactory.getLogger(MobileServiceImpl.class);
	//读取手机号真实姓名
	@Resource(name = "mobileNameService")
	private IMobileNameService mobileNameService = null;
	//读取手机号归属地(readAddressFromMoblie)
	@Resource(name="mobileAddressService")
	private IMobileAddressService mobileAddressService=null;
	@Resource(name = "mobileAttributionDAO")
	private MobileAttributionDAO mobileAttributionDAO = null;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@Override
	public Map<String, Object> readMobileInfo(String mobile) throws BusinessException {
		return mobileAddressService.readAddressFromMoblie(mobile);
	}
	
	@Override
	public Map<String, Object> readMobileDynamicInfo(final String mobile) throws BusinessException {
		List<Future<Map<String, Object>>> fs = new ArrayList<Future<Map<String, Object>>>();
		Future<Map<String, Object>> f_n = executorService.submit(new Callable<Map<String, Object>>() {
			@Override
			public Map<String, Object> call() throws Exception {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("姓名", mobileNameService.readNameFromMobile(mobile));
				return map;
			}
		});
		fs.add(f_n);
		Future<Map<String, Object>> f_a = executorService.submit(new Callable<Map<String, Object>>() {
			@Override
			public Map<String, Object> call() throws Exception {
				return mobileAddressService.readAddressFromMoblie(mobile);
			}
		});
		fs.add(f_a);
		TreeMap<String,Object> resultMap = new TreeMap<String, Object>();
		try {
			for (Future<Map<String, Object>> f : fs) {
				while (!f.isDone()) {}
				resultMap.putAll(f.get());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return resultMap;
	}

	@Override
	public List<MobileAttributionModel> selByDnseg(String phone) {
		//截取字符串
		String mobile = phone.substring(0, 7);
		return mobileAttributionDAO.selByDnseg(mobile);
	}
	
}
