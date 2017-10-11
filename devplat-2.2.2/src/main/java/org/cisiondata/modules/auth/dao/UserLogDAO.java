package org.cisiondata.modules.auth.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("authUserLogDAO")
public interface UserLogDAO {
	//查询用户今日访问接口次数         没用
	public int readByCountx(String account,String url)throws DataAccessException;;
	
	//查询用户进入访问接口的次数 和关键查询次数            没用
	public Map<String,Object>  readLogCountx(String account)throws DataAccessException;;
	//查询指定资源限制是否存在  在用  已改在CompanyResourceDAO
	public Map<String,Object> readCompanyLimit(Long companyId)throws DataAccessException;;
	//查询角色限制      在用   已经改在RoleLimit
	public Map<String,Object> readRoleLimit(Long companyId)throws DataAccessException;
	//查询角色对特定资源限制 在用
	public Map<String,Object> readRoleSpecialLimit(@Param("url") String url,@Param("companyId") Long companyId) throws DataAccessException;
	
	
	//判断是否单位限制 在用
	public Map<String,Object> judgeCompanyLimit(Long companyId)throws DataAccessException;
	
	
	//读取单位的角色id 在用
	public Long readCompanyRoleId(Long companyId)throws DataAccessException;
	
	
	//读取单位限制当前url的限制条数 在用
	public Map<String,Object> readCompanyUrlLimit(@Param("companyId") Long companyId,@Param("url") String url ) throws DataAccessException;
	
	
	
	
	
	//获取当前访问的总次数 与返回的条数 （1为单位限制类型 2 为角色限制类型） 在用
	public Map<String,Object> readCurrentAccess(@Param("type")Integer type,@Param("typeId") Long companyId)throws DataAccessException;
	
	//获取当前url访问的次数 在用
	public Integer readCurrentUrlAccess(@Param("type")Integer type, @Param("typeId") Long companyId,@Param("url") String url )throws DataAccessException;

	//如果访问记录与记录的日志访问记录不一致跟新记录 在用
	public void updateAccess(@Param("type")Integer type,@Param("count")Integer count,@Param("access")Integer access)throws DataAccessException;
	
	
	
	
	//读取全部单位id  在用
	public List<Long> readAllCompanyId() throws DataAccessException;
	
	
	//读取全部单位对应的角色 在用
	public List<Map<String,Object>> readAllRoleId() throws DataAccessException;
	
	
	
	
	
	
	
	
}
