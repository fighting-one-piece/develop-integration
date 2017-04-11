package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResources;
import org.springframework.stereotype.Repository;

@Repository("AResourcesDao")
public interface AResourcesDAO {
	// 查询全部资源
	public List<AResources> selResource();

	// 查询当前角色已授权的资源
	public List<AResources> selResourceById(Long roleId);

	// 保存资源
	public int addPermission(APermission ps);

	// 删除资源
	public int delPermission(Long roleId);
}
