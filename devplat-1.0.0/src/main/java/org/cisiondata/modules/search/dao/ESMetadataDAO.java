package org.cisiondata.modules.search.dao;

import java.util.List;

import org.cisiondata.modules.search.entity.ESMetadata;
import org.springframework.stereotype.Repository;

@Repository("esMetadataDAO")
public interface ESMetadataDAO {
	//根据indx查询
	public List<ESMetadata> findType(String type);
	
	//根据id删除
	public int deleteId(int id);
	
	//添加
	public void save(ESMetadata metadata);
	
	//修改
	public int updateId(ESMetadata metadata);
}
