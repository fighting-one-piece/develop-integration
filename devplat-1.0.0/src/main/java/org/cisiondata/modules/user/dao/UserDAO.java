package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.Security;
import org.cisiondata.modules.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository("auserDAO")
public interface UserDAO {
	//通过账号修改资料
	public void updateUser(User user);
	//查询密保问题
	public List<Security> findSecurity();
	//获取用户密保问题 
	public String findSecurityQuestion(String account);
	//获取用户密保问题答案 
	public String findSecurityAnswer(String account);
	//获取用户密码
	public User findUser(String account);
}
