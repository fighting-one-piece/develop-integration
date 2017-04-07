package org.cisiondata.modules.user.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class AdminUser extends PKAutoEntity<Long>{

	private static final long serialVersionUID = 1L;

	/** 账号*/
	private String account = null;
	/** 密码*/
	private String password = null;
	/** 用户标识*/
	private String identity = null;
	/** 用户昵称*/
	private String nickName = null;
	/** 是否删除标识*/
	private Boolean deleteFlag = Boolean.FALSE;
	/** 加密字段*/
	private String salt = null;
	/** 用户电话*/
	private String mobilePhone = null;
	
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
}
