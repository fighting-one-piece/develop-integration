package org.cisiondata.modules.admin.statistics.service;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.admin.statistics.entity.EventBase;
import org.cisiondata.modules.admin.statistics.entity.EventExtend;

public interface IEventBaseSerivce extends IGenericService<EventBase, Long> {
	// 增加
	public int addEvent(EventBase event);

	// 增加事件详情
	public void addExtend(EventExtend extend);
	
	//查询是否存在该名称
	public int selEvent(String name);
}
