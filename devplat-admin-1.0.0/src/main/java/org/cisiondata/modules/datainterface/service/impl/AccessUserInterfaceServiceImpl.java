package org.cisiondata.modules.datainterface.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.datainterface.dao.AccessInterfaceDao;
import org.cisiondata.modules.datainterface.dao.AccessUserControlDao;
import org.cisiondata.modules.datainterface.dao.AccessUserInterfaceDAO;
import org.cisiondata.modules.datainterface.entity.AccessInterface;
import org.cisiondata.modules.datainterface.entity.AccessUserControl;
import org.cisiondata.modules.datainterface.entity.AccessUserInterface;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceMoneyService;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现类
 * @author Administrator
 *
 */
@Service("accessUserInterfaceService")
public class AccessUserInterfaceServiceImpl implements IAccessUserInterfaceService{
	
	@Resource(name="AccessUserInterfaceDAO")
	private AccessUserInterfaceDAO accessUserInterfaceDAO;
	
	@Resource(name="aaccessUserControlDao")
	private AccessUserControlDao accessUserControlDao;
	@Resource(name="accessInterfaceDao")
	private AccessInterfaceDao accessInterfaceDao;
	
	@Resource(name = "accessUserInterfaceMoneyService")
	private IAccessUserInterfaceMoneyService accessUserInterfaceMoneyService;

	//分页数据
	@Override
	public Map<String,Object> Pagination(int page, int pageSize)throws BusinessException {
		//计算分化
		int contun=accessUserInterfaceDAO.contun();
		int pageCount = contun % pageSize == 0 ? contun/pageSize : contun/pageSize + 1;
		int begin = (page-1) * pageSize;
		List<AccessUserInterface> read=accessUserInterfaceDAO.Pagination(begin,pageSize);
		Map<String, Object> map = new HashMap<String ,Object>();
		System.out.println(read);
		map.put("data",read);
		map.put("pageNum", page);
		map.put("pageCount", pageCount);
		return map;
	}
	//id查询数据
	@Override
	public AccessUserInterface QueryId(long id) {
		AccessUserInterface Interface=accessUserInterfaceDAO.QueryId(id);
		System.out.println(Interface);
		return Interface;
	}
	@Override
	public int Revamp(int Number,long id) {
		return accessUserInterfaceDAO.Revamp(Number,id);
	}
	@Override
	public int RemoveUser(long id) {
		return accessUserInterfaceDAO.RemoveUser(id);
	}
	
	@Override
	public List<String> userAccount() {
		List<String> userControl =accessUserControlDao.findAccessControlAll();
		return userControl;
	}
	@Override
	public List<AccessInterface> hickey() {
		List<AccessInterface> hickey=accessInterfaceDao.findAll();
		return hickey;
	}
	
	public int addUserAccount(AccessUserInterface UserInterface) {	
		 int suucess=accessUserInterfaceDAO.addUserAccount(UserInterface);
		 Long id=UserInterface.getId();
		 if(suucess==1){
		 accessUserInterfaceMoneyService.addAccessUserInterface(id,UserInterface.getInterfaceId());
		 }
		return  suucess;
	}
	//批量赋予
	@Override
	public int giveHickey(String user) {
		//获取到账号的ID值
		AccessUserControl countId=accessUserControlDao.findAccessControlByAccount(user);
		countId.getAccount();
		//读取所有的接口ID
		List<AccessInterface> Interface=accessInterfaceDao.findAll();
		int bb = 0;
		System.out.println(Interface);
		for (int i = 0; i < Interface.size(); i++) {
			Interface.get(i).getId();
			bb=accessUserInterfaceDAO.giveHickey(countId.getAccount(), Interface.get(i).getId());	
		}
		//循环将数据赋予进入
		
		return bb;
	}
}
