package org.cisiondata.modules.datainterface.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessSwitchDAO;
import org.cisiondata.modules.datainterface.entity.AccessSwitch;
import org.cisiondata.modules.datainterface.service.IAccessSwitchService;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("aaccessSwitchService")
public class AccessSwitchServiceImpl implements IAccessSwitchService, InitializingBean {
	
	@Resource(name = "swithDAO")
	private AccessSwitchDAO swithDAO;
	
	//初始化开关缓存
	@Override
	public void afterPropertiesSet() throws Exception {
		List<AccessSwitch> list = swithDAO.findAll();
		System.out.println(list);
		if (null == list || list.size() == 0) return;
		for (AccessSwitch sSwitch : list) {
			RedisClusterUtils.getInstance().set(sSwitch.getSwitch_identity(), sSwitch.getStatus());
		}
	}
	
	//查询所有表示
	@Override
	public List<AccessSwitch> findAll() {
		List<AccessSwitch> list = new ArrayList<AccessSwitch>();
		list = swithDAO.findAll();
		return list;
	}
	
	//添加save
	@Override
	public void saveSwitch(AccessSwitch sSwitch) throws IOException {
		swithDAO.saveSwitch(sSwitch);
		RedisClusterUtils.getInstance().set(sSwitch.getSwitch_identity(), sSwitch.getStatus());
	}
	
	//删除
	@Override
	public void deleteIdentity(String identity) throws IOException {
		swithDAO.deleteIdentity(identity);
		RedisClusterUtils.getInstance().delete(identity);
	}
	
	//修改
	@Override
	public int updateId(AccessSwitch sSwitch) throws IOException {
		RedisClusterUtils.getInstance().set(sSwitch.getSwitch_identity(), sSwitch.getStatus());
		return swithDAO.updateId(sSwitch);
	}
}
