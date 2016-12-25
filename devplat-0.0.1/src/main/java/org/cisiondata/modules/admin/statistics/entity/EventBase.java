package org.cisiondata.modules.admin.statistics.entity;

import java.util.Date;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class EventBase extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	/**事件ID*/
	private int eventId;
	/** 事件名称 */
	private String eventName = null;
	/** 事件时间 */
	private Date eventTime = null;
	/** 事件信息 */
	private String eventInfo = null;
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public Date getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventInfo() {
		return eventInfo;
	}
	
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	
	
	
}
