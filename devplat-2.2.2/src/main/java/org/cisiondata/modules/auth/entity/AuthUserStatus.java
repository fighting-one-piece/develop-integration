package org.cisiondata.modules.auth.entity;

public enum AuthUserStatus {

	OFFLINE(0),
	ONLINE(1);
	
	private int value = 0;
	
	private AuthUserStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
