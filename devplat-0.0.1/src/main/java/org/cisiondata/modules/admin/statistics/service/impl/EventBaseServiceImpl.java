package org.cisiondata.modules.admin.statistics.service.impl;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.admin.statistics.dao.EventBaseDAO;
import org.cisiondata.modules.admin.statistics.entity.EventBase;
import org.cisiondata.modules.admin.statistics.entity.EventExtend;
import org.cisiondata.modules.admin.statistics.service.IEventBaseSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventBaseServiceImpl extends GenericServiceImpl<EventBase, Long> implements IEventBaseSerivce{

	@Autowired
	private EventBaseDAO eventBaseDAO = null;
	
	@Override
	public GenericDAO<EventBase, Long> obtainDAOInstance() {
		return eventBaseDAO;
	}
	
	@Override
	public int addEvent(EventBase event) {
		int reust = eventBaseDAO.addEvent(event);// TODO Auto-generated method stub
		return reust;
	}
	
	@Override
	public void addExtend(EventExtend extend) {
		// TODO Auto-generated method stub
		eventBaseDAO.addExtend(extend);
	}
	
	@Override
	public int selEvent(String name) {
		return eventBaseDAO.selEvent(name);
	}
	
}
