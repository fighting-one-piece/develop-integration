package org.cisiondata.modules.analysis.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.springframework.stereotype.Repository;

@Repository("eventExtendDAO")
public interface EventExtendDAO extends GenericDAO<EventExtend, Long>{
	
	//获得所有的信息
	public List<EventExtend> readEventInfo(String fileName);
	 
	//执行写入操作
	public int Updata(String resultListJson,Long long1);
	
	//查询所有的数据结果
	public List<String> readall(int ID);
	
	//匹配表名，获得ID
	public int findbaseID(String filname);
	
	//修改查询分析的数据状态
	public int codes(long id);
	
	//统计条数
	public int count(long id);
}
