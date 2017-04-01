package org.cisiondata.modules.auth.entity;

/** 资源计费类型*/
public enum ResourceChargingType {
	
	DAY(0), MONTH(1), QUARTER(2), YEAR(3);
	
	private int value;
	
	private ResourceChargingType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
