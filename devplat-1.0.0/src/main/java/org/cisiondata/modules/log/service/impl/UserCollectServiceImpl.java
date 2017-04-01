package org.cisiondata.modules.log.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.log.dao.UserCollectDAO;
import org.cisiondata.modules.log.entity.UserCollect;
import org.cisiondata.modules.log.service.IUserCollectService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;
@Service("userCollectService")
public class UserCollectServiceImpl implements IUserCollectService{
	@Resource(name="userCollectDAO")
	private UserCollectDAO collectDao = null; 
	
	public void addUserCollect(String account,String type,String collect) {
		UserCollect coll = new UserCollect();
		if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(collect)){
			coll.setAccount(account);
			coll.setType(type);
			coll.setCollectTime(new Date());
			coll.setCollectContent(collect);
			int code = collectDao.addUserCollect(coll);
			if(code != 1){
				throw new BusinessException(ResultCode.DATABASE_OPERATION_FAIL);
			}
		}else{
			throw new BusinessException(ResultCode.PARAM_NULL);
		}
	}

	public List<UserCollect> selCollect(String account) {
		List<UserCollect> list = collectDao.selCollect(account);
		if(list.size() == 0){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		}
		return list;
	}

}
