package org.cisiondata.modules.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.user.dao.AdminUserDAO;
import org.cisiondata.modules.user.entity.AdminUser;
import org.cisiondata.modules.user.service.IAdminUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService {

	@Resource(name = "aadminUserDAO")
	private AdminUserDAO adminUserDAO;
	
	@Override
	public QueryResult<AdminUser> findAdminUsersByPage(Integer page, Integer pageSize) throws BusinessException {
		if(page < 1 || pageSize < 1) throw new BusinessException(ResultCode.PARAM_ERROR);
		Map<String,Object> params = new HashMap<String,Object>();
		int count = adminUserDAO.findCountByCondition(params);
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		params = new HashMap<String,Object>();
		params.put("begin", begin);
		params.put("pageSize", pageSize);
		List<AdminUser> list = adminUserDAO.findByCondition(params);
		if (list.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		QueryResult<AdminUser> qr = new QueryResult<AdminUser>();
		qr.setResultList(list);
		qr.setTotalRowNum(pageCount);
		return qr;
	}

	@Override
	public boolean addAdminUser(AdminUser adminUser) throws BusinessException {
		boolean b=false;
		
		if( adminUser.getAccount().trim().length()==0||adminUser.getPassword().trim().length()==0)throw new BusinessException(ResultCode.PARAM_NULL);
		try {
			int 	count=adminUserDAO.addAdminUser(adminUser);	
			if(count==1){
				b=true;
			}
		} catch (DuplicateKeyException e) {
			throw new BusinessException(ResultCode.DATA_EXISTED);
		}

		return b;
	}

	
	@Override
	public boolean deleteAdminUser(long id) throws BusinessException {
		boolean b=false;
		try {
			int i= adminUserDAO.deleteAdminUserById(id);
			if(i>0){
				b=true;
			}
		} catch (Exception e) {
			throw new BusinessException(ResultCode.VERIFICATION_NO_EXIST);
		}
		return b;
		
		
	}

	@Override
	public boolean updateAdminUser(AdminUser adminUser) throws BusinessException {
		boolean b=false;
		if( adminUser.getAccount().trim().length()==0||adminUser.getPassword().trim().length()==0)throw	new BusinessException(ResultCode.PARAM_NULL);
		int count=adminUserDAO.updateAdminUserById(adminUser);
		if(count==1){
			b=true;
		}
		return b;
	}

}
