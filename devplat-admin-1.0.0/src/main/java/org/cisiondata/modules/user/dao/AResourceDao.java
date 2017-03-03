package org.cisiondata.modules.user.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.user.entity.AResource;
import org.springframework.stereotype.Repository;

@Repository("aResourceDao")
public interface AResourceDao extends GenericDAO<AResource, Long> {

	// 增加链接
	public int addAResource(AResource aResource);

	// 查询全部链接
	public List<AResource> findAllAResource();

	// 根据状态查询链接
	public List<AResource> findByDeleteFlag();

	// 根据ID删除
	public int updateDeleteFlagById(Long id);

	// 根据ID修改parentId
	public int updateParentIdByParentId(Long id);
	
	// 根据type查找
	public List<AResource> findByType(int type);
	
	// 分页查找
	public List<AResource> findByPage(int begin ,int end);
	
	//根据Id查找
	public AResource findById(Long id);
	
	//根据ID修改
	public int updateAResource(AResource aResource);
	
	//根据type获取条数
	public int findCount();
	
	//根据type和parentId查询 
	public List<AResource> findByTypeAndParentId(AResource aResource);
	
	//根据parentId查询 
	public List<AResource> findByParentId(String parentId);
}
