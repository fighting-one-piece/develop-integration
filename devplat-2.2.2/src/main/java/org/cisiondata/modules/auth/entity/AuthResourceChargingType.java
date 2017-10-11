package org.cisiondata.modules.auth.entity;

/** 资源计费类型*/
public enum AuthResourceChargingType {
	
	QUERY_TIMES(0), 
	RESULT_COUNT(1),
	DAY(2), MONTH(3), 
	QUARTER(4), 
	YEAR(5);
	
	private int value;
	
	private AuthResourceChargingType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
