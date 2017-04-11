package org.cisiondata.modules.user.service;

import java.util.List;

import org.cisiondata.modules.user.entity.AResources;

public interface IAResourceService {
	// 查询全部资源
	public List<AResources> selResource();

	// 查询当前角色已授权的资源
	public List<AResources> selResourceById(Long roleId);

	// 保存资源
	public String addPermission(String resourceId, Long roleId);
}
