package org.cisiondata.modules.user.service;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.user.entity.APermission;
import org.cisiondata.modules.user.entity.AResource;
import org.cisiondata.utils.exception.BusinessException;

public interface IAResourceService {

	/* 查询全部 */
	public List<AResource> findAll() throws BusinessException;
	
	/* 增加 */
	public int addLink(AResource aResource) throws BusinessException;

	/* 根据状态查询 */
	public List<AResource> findByDeleteFlag() throws BusinessException;

	/* 根据ID删除 */
	public int deleteById(Long id) throws BusinessException;

	/* 查询菜单栏链接 */
	public List<AResource> findMenuLink() throws BusinessException;
	
	/* 根据id查询资源*/
	public AResource findById(Long id) throws BusinessException;
	
	/* 根据id修改资源*/
	public int updateById(AResource aResource) throws BusinessException;
	
	/* 根据资源类型查询*/
	public List<AResource> findByType(int type) throws BusinessException;
	
	/* 根据资源类型分页查询*/
	public Map<String, Object> findByPage(int page , int pageSize) throws BusinessException;
	
	/* 根据type、parentId查询*/
	public List<AResource> findAdminMenu(AResource aResource) throws BusinessException;
	
	/* 获取资源树形结构*/
	public List<AResource> findResourceTree() throws BusinessException;
	
	public void updatePermission(String param) throws BusinessException;
	
	public Map<String,Object> findPermissions(APermission permission) throws BusinessException;
}
