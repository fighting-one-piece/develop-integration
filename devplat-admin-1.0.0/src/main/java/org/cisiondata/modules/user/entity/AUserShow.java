package org.cisiondata.modules.user.entity;


public class AUserShow extends AUser {

	private static final long serialVersionUID = 1L;
	
	/** 用户ID */
	private Long userId = null;
	/** 用户账号 */
	private String account = null;
	/** 用户真名 */
	private String realname = null;
	/** 用户角色 */
	private String role = null;
	/** 充值金额 */
	private Double  money = null;
	/** 余额 */
	private Double remainingMoney = null;
	public Double getRemainingMoney() {
		return remainingMoney;
	}
	public void setRemainingMoney(Double remainingMoney) {
		this.remainingMoney = remainingMoney;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
