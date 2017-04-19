package org.cisiondata.modules.elasticsearch.dao;

import org.cisiondata.modules.elasticsearch.entity.LMData;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("lmDataDAO")
public interface LMDataDAO {
	//添加
    int insert(LMData record)throws DataAccessException;
}