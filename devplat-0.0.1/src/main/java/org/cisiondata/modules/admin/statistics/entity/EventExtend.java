package org.cisiondata.modules.admin.statistics.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class EventExtend extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 事件ID*/
	private Long eventId = null;
	/** 扩展信息1*/
	private String extendInfo1 = null;
	/** 扩展信息2*/
	private String extendInfo2 = null;
	/** 扩展信息3*/
	private String extendInfo3 = null;
	/** 扩展信息4*/
	private String extendInfo4 = null;
	/** 扩展信息5*/
	private String extendInfo5 = null;
	//外键ID 
	private int eventBaseid;
	public Long getEventId() {
		return eventId;
	}
	
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String getExtendInfo1() {
		return extendInfo1;
	}
	
	public void setExtendInfo1(String extendInfo1) {
		this.extendInfo1 = extendInfo1;
	}
	
	public String getExtendInfo2() {
		return extendInfo2;
	}
	
	public void setExtendInfo2(String extendInfo2) {
		this.extendInfo2 = extendInfo2;
	}
	
	public String getExtendInfo3() {
		return extendInfo3;
	}
	
	public void setExtendInfo3(String extendInfo3) {
		this.extendInfo3 = extendInfo3;
	}
	
	public String getExtendInfo4() {
		return extendInfo4;
	}
	
	public void setExtendInfo4(String extendInfo4) {
		this.extendInfo4 = extendInfo4;
	}
	
	public String getExtendInfo5() {
		return extendInfo5;
	}
	
	public void setExtendInfo5(String extendInfo5) {
		this.extendInfo5 = extendInfo5;
	}

	public int getEventBaseid() {
		return eventBaseid;
	}

	public void setEventBaseid(int eventBaseid) {
		this.eventBaseid = eventBaseid;
	}

	
	
}

