package org.cisiondata.modules.system.entity;

import java.io.Serializable;

public class IdCardAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 身份证前6位*/
	private String idCard;
	/** 该身份证号对应的地址*/
	private String address;
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
