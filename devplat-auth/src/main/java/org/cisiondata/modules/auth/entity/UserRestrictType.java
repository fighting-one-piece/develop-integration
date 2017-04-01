package org.cisiondata.modules.auth.entity;

/** 用户限制类型*/
public enum UserRestrictType {
	
	DAY(0), MONTH(1), QUARTER(2), YEAR(3);
	
	private int value;
	
	private UserRestrictType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
