package org.cisiondata.modules.system.entity;

public class Resource extends org.cisiondata.modules.auth.entity.Resource {

	private static final long serialVersionUID = 1L;
	
	/** 字段以及处理方式*/
	private String fields = null;
	/** 收费*/
	private String chargings = null;
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getCharge() {
		return chargings;
	}
	public void setCharge(String charge) {
		this.chargings = charge;
	}

}
