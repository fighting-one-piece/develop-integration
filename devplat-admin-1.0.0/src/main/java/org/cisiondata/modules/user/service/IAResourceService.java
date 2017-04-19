package org.cisiondata.modules.user.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.user.entity.AResource;

public interface IAResourceService {

	// 查询当前角色已授权的资源
	public List<AResource> selResourceById(Long roleId);

	// 保存资源
	public String addPermission(String resourceId, Long roleId);

	// 查询角色已授权资源
	public List<AResource> selResourceByUr(Long userId);

	// 保存用户资源
	public String addResourceByUr(Long userId, String resourceId);

	// 更新删除标识
	public String updateURByFlag(int userId, int resourceId, Boolean deleteFlag);

	// 分页查询--用户资源关系
	public Map<String, Object> selByUR(int index, int pageSize);

	// 分页查询--根据账号查询用户定制资源的关系
	public Map<String, Object> selUrByName(String name, int index, int pageSize);

	/** API */

	// 查询当前角色已授权的资源API
	public List<AResource> selApiResourceById(Long roleId);

	// 保存资源API
	public String addApiPermission(String resourceId, Long roleId);

	// 查询角色已授权资源
	public List<AResource> selApiResourceByUr(Long userId);

	// 分页查询--用户资源关系API
	public Map<String, Object> selByApiUR(int index, int pageSize);

	// 更新删除标识
	public String updateApiURByFlag(int userId, int resourceId, Boolean deleteFlag);

	// 保存用户资源
	public String addApiUserResource(Long userId, String resourceId);

	/** 警友通 */
	// 查询全部资源
	public List<AResource> selJYTResource();

	// 查询当前角色已授权的资源
	public List<AResource> selJYTResourceById(Long roleId);

	// 保存资源
	public String addJYTPermission(String resourceId, Long roleId);
}
