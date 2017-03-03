package org.cisiondata.modules.analysis.dao;



import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.analysis.entity.EventBase;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.springframework.stereotype.Repository;

@Repository("eventBaseDao")
public interface EventBaseDAO extends GenericDAO<EventBase, Long>{
	//增加
	public int addEvent(EventBase event);
	
	//增加事件详情
	public void addExtend(EventExtend extend);
	
	//查询是否存在该名称
	public int selEvent(String name);
	
	//查询当前分析的数据
	public List<EventExtend> selExtend(String filename);
	
	//查询当前分析的数据进行分页
	public List<EventExtend> selExtendPage(String name,int startPos,int pageSize);
	
	//查询全部所有
	public List<EventBase> findAll();
	
	//查询当前所分析后的文件名分页
	public List<EventBase> selEventBase(int startPos,int pageSize);
	
	//删除所选的文件名
	public int delEventBase(String name);
	
	//删除所选的文件分析数据
	public int delExtend(String name);
}
