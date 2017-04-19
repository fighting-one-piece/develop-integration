package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.auth.entity.UserResource;
import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResource;
import org.cisiondata.modules.user.entity.AUserResource;
import org.springframework.stereotype.Repository;

@Repository("AResourcesDao")
public interface AResourcesDAO {
	// 查询全部资源
	public List<AResource> selResource();

	// 查询当前角色已授权的资源
	public List<AResource> selResourceById(Long roleId);

	// 保存资源
	public int addPermission(List<APermission> list);

	// 删除资源
	public int delPermission(Long roleId);

	// 查询角色已授权资源
	public List<AResource> selResourceByUr(Long userId);

	// 更新全部删除状态
	public int updateByUser(Long userId);

	// 根据用户、资源更新状态
	public int updateByUr(UserResource ur);

	// 查询是否存在数据
	public int selByUr(Long userId, Long resourceId);

	// 新增资源
	public int addUserResource(List<UserResource> list);

	// 查询总条数
	public List<AUserResource> selByUrPage();

	// 分页查询--用户资源关系
	public List<AUserResource> selByUR(int index, int pageSize);

	// 更新删除标识状态
	public int updateURByFlag(int userId,int resourceId);

	public int updateURFlag(int userId,int resourceId);

	// 查询总条数
	public List<AUserResource> selUrByPage(String name);

	// 分页查询--根据账号查询用户定制资源的关系
	public List<AUserResource> selUrByName(String name, int index, int pageSize);

	/** API */
	// 查询资源Api
	public List<AResource> selApiResource();

	// 查询已授权的资源API
	public List<AResource> selApiResourceById(Long roleId);

	// 删除资源API
	public int delApiPermission(Long roleId);

	// 分页查询--用户资源关系API
	public List<AUserResource> selByApiUR(int index, int pageSize);

	// 查询总条数API
	public List<AUserResource> selByApiUrPage();

	// 更新删除标识状态
	public int updateApiURByFlag(int userId,int resourceId);

	public int updateApiURFlag(int userId,int resourceId);

	// 查询角色已授权资源
	public List<AResource> selApiResourceByUr(Long userId);

	// 新增资源API
	public int addApiUserResource(List<UserResource> list);

	// 更新全部删除状态
	public int updateApiByUser(Long userId);

	/** 警友通 */
	// 查询全部资源
	public List<AResource> selJYTResource();

	// 查询当前角色已授权的资源
	public List<AResource> selJYTResourceById(Long roleId);

	// 保存资源
	public int addJYTPermission(List<APermission> list);

	// 删除资源
	public int delJYTPermission(Long roleId);
}
