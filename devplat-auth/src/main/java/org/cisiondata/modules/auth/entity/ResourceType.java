package org.cisiondata.modules.auth.entity;

public enum ResourceType {
	
	MENU(1), MODULE(2), INTERFACE(3), API(5), APP(6);
	
	private int value;
	
	private ResourceType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
