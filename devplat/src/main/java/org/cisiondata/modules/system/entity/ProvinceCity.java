package org.cisiondata.modules.system.entity;

import java.io.Serializable;

public class ProvinceCity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String province = null;
	
	private String city = null;
	
	private String reginNum = null;
	
	private String operator = null;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReginNum() {
		return reginNum;
	}

	public void setReginNum(String reginNum) {
		this.reginNum = reginNum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	

}
