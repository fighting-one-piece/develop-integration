package org.cisiondata.modules.datainterface.entity;

public enum SwitchStatus {
	
	DEMO(0),  NORMAL(1),TEST(2);
	
	private int value = 0;

	private SwitchStatus(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	public static SwitchStatus getStatus(int value) {
		switch (value) {
		case 0:
			return SwitchStatus.DEMO;
		case 1:
			return SwitchStatus.TEST;
		case 2:
			return SwitchStatus.NORMAL;
		default:
			return SwitchStatus.DEMO;
		}
		
	}
}
