package org.cisiondata.modules.admin.statistics.dao;



import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.admin.statistics.entity.EventBase;
import org.cisiondata.modules.admin.statistics.entity.EventExtend;
import org.springframework.stereotype.Repository;

@Repository("eventBaseDao")
public interface EventBaseDAO extends GenericDAO<EventBase, Long>{
	//增加
	public int addEvent(EventBase event);
	
	//增加事件详情
	public void addExtend(EventExtend extend);
	
	//查询是否存在该名称
	public int selEvent(String name);
}
