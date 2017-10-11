package org.cisiondata.modules.auth.entity;

public enum AuthResourceType {
	
	MENU(1),
	MODULE(2),
	MIRROR_WORLD(3),
	API(5),
	APP(6);
	
	private int value;
	
	private AuthResourceType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
