package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.auth.entity.UserAttribute;
import org.cisiondata.modules.user.entity.Security;
import org.cisiondata.modules.user.entity.AUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("auserDAO")
public interface UserDAO {
	//通过账号修改资料
	public void updateUser(AUser user)  throws DataAccessException;
	//查询密保问题
	public List<Security> findSecurity()  throws DataAccessException;
	//获取用户密保问题 
	public String findSecurityQuestion(String account)  throws DataAccessException;
	//获取用户密保问题答案 
	public String findSecurityAnswer(String account)  throws DataAccessException;
	//通过账号获取用户
	public AUser findUser(String account)  throws DataAccessException;
	//通过电话获取用户
	public AUser findUserByPhone(String phone)  throws DataAccessException;
	//修改用户各种单一属性
	public void editUserAttribute(UserAttribute userAttribute)  throws DataAccessException;
	//插入用户各种单一属性
	public void addUserAttribute(UserAttribute userAttribute)  throws DataAccessException;
	//查看用户各种单一属性
	public String findUserAttribute(UserAttribute userAttribute)  throws DataAccessException;
}
