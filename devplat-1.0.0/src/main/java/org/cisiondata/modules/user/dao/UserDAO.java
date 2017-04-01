package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.Security;
import org.cisiondata.modules.user.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserDAO")
public interface UserDAO {
	//通过账号修改资料
	public void updateUser(User user)  throws DataAccessException;
	//查询密保问题
	public List<Security> findSecurity()  throws DataAccessException;
	//获取用户密保问题 
	public String findSecurityQuestion(String account)  throws DataAccessException;
	//获取用户密保问题答案 
	public String findSecurityAnswer(String account)  throws DataAccessException;
	//通过账号获取用户
	public User findUser(String account)  throws DataAccessException;
	//通过电话获取用户
	public User findUserByPhone(String phone)  throws DataAccessException;
}
