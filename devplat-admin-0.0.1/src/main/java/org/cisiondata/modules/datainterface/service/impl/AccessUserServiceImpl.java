package org.cisiondata.modules.datainterface.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.datainterface.dao.AccessUserDAO;
import org.cisiondata.modules.datainterface.entity.AccessUser;
import org.cisiondata.modules.datainterface.service.IAccessUserService;
import org.cisiondata.utils.endecrypt.MD5Utils;
import org.cisiondata.utils.endecrypt.SHAUtils;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("aaccessUserService")
public class AccessUserServiceImpl extends GenericServiceImpl<AccessUser, Long> implements IAccessUserService {
	
	@Resource(name = "aaccessUserDAO")
	private AccessUserDAO accessUserDAO;
	
	@Override
	public GenericDAO<AccessUser, Long> obtainDAOInstance() {
		return accessUserDAO;
	}
	
	@Override
	public int addAccessControl(AccessUser accessControl) throws BusinessException {
		if (accessUserDAO.findByName(accessControl).size() == 0) {
			
			accessControl.setApplyTime(new Date());
			String name = accessControl.getName();
			Date date = accessControl.getApplyTime();
			
			//MD5(SHA1(name+applyTime)).substring(8,24)
			//MD5(SHA1(name+applyTime+accessId))
			String accessId = MD5Utils.hash(SHAUtils.SHA1(name + date.toString())).substring(8, 24);
			accessId = toRandomUpper(accessId);
			String accessKey = MD5Utils.hash(name + date.toString() + accessId);
			accessKey = toRandomUpper(accessKey);
			
			accessControl.setAccessKey(accessKey);
			accessControl.setAccessId(accessId);
			return accessUserDAO.addAccessUser(accessControl);
		} else {
			return 0;
		}
	}

	
	@Override
	public Map<String,Object> findAccessUserByPage(int page, int pageSize) throws BusinessException {
		int count = accessUserDAO.findAccessCount();
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		List<AccessUser> accessList = accessUserDAO.findAccessByPage(begin, pageSize);
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("data", accessList);
		map.put("pageNum", page);
		map.put("pageCount", pageCount);
		return map;
	}
	
	@Override
	public int updateDeleteFlag(AccessUser accessUser) throws BusinessException {
		return accessUserDAO.updateDeleteFlag(accessUser);
	}
	
	
	
	
	/**
	 * 将字符串部分字母转换为大写
	 * @param str
	 * @return
	 */
	private String toRandomUpper(String str){
		char c[] = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i =0; i < c.length; i++){
			if (Character.isLetter(c[i])) {
				if (i%3 !=0 || i==0) {
					sb.append((c[i]+"").toUpperCase());
				} else {
					sb.append(c[i]);
				}
				
			} else {
				sb.append(c[i]);
			}
		}
		return sb.toString();
	}
	
	@Override
	public String readAccessKeyByAccessId(String accessId) throws BusinessException {
		if (StringUtils.isBlank(accessId)) throw new BusinessException("accessId不能为空");
		Query query = new Query();
		query.addCondition("accessId", accessId);
		query.addCondition("deleteFlag", false);
		AccessUser accessUser = accessUserDAO.readDataByCondition(query);
		if (null == accessUser) throw new BusinessException("未找到对应accessKey或accessKey无效");
		return accessUser.getAccessKey();
	}

}
