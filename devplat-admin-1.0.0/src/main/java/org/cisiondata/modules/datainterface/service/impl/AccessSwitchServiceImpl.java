package org.cisiondata.modules.datainterface.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessSwitchDAO;
import org.cisiondata.modules.datainterface.entity.AccessSwitch;
import org.cisiondata.modules.datainterface.service.IAccessSwitchService;
import org.cisiondata.utils.excel.PoiExcelUtils;
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
		if (null == list || list.size() == 0) return;
		for (AccessSwitch accessSwitch : list) {
			RedisClusterUtils.getInstance().set(accessSwitch.getSwitch_identity(), accessSwitch.getStatus());
		}
	}
	
	//查询所有表示
	@Override
	public List<AccessSwitch> findAll() {
		List<AccessSwitch> list = new ArrayList<AccessSwitch>();
		list = swithDAO.findAll();
		return list;
	}
	
	//添加
	@Override
	public void saveSwitch(AccessSwitch accessSwitch) throws IOException {
		swithDAO.saveSwitch(accessSwitch);
		RedisClusterUtils.getInstance().set(accessSwitch.getSwitch_identity(), accessSwitch.getStatus());
	}
	
	//删除
	@Override
	public void deleteIdentity(String identity) throws IOException {
		swithDAO.deleteIdentity(identity);
		RedisClusterUtils.getInstance().delete(identity);
	}
	
	//修改
	@Override
	public int updateId(AccessSwitch accessSwitch) throws IOException {
		RedisClusterUtils.getInstance().set(accessSwitch.getSwitch_identity(), accessSwitch.getStatus());
		return swithDAO.updateId(accessSwitch);
	}
	
	//批量修改
	@Override
	public void updateIdStatus(String id,String identity,Integer status) throws IOException {
		List<String> listId = PoiExcelUtils.stringToList(id);
		List<String> listIdentity = PoiExcelUtils.stringToList(identity);
		for (int i = 0; i < listId.size(); i++) {
			AccessSwitch accessSwitch = new AccessSwitch();
			accessSwitch.setId(Integer.valueOf(listId.get(i)));
			accessSwitch.setStatus(status);
			swithDAO.updateIdStatus(accessSwitch);
			RedisClusterUtils.getInstance().set(listIdentity.get(i), status);
		}
	}
}
