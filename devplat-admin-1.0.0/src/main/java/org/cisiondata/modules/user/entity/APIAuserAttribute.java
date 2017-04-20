package org.cisiondata.modules.user.entity;


public class APIAuserAttribute {

	
	
	/**ACCESS_ID*/
	private String accessId=null;
	/**ACCESS_KEY*/
	private String accessKey=null;
	/**充值金额*/
	private	String  money =null;
	/**剩余金额*/
	private	String   remainingMoney =null;
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRemainingMoney() {
		return remainingMoney;
	}
	public void setRemainingMoney(String remainingMoney) {
		this.remainingMoney = remainingMoney;
	}
	
	
}
