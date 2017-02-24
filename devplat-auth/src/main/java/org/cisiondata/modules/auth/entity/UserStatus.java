package org.cisiondata.modules.auth.entity;

public enum UserStatus {

	OFFLINE(0), ONLINE(1);
	
	private int value = 0;
	
	private UserStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
