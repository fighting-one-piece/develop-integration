package org.cisiondata.modules.login.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户账号 */
	private String account = null;
	/** 用户昵称*/
	private String nickname = null;
	/** 访问令牌*/
	private String accessToken = null;
	/** 是否首次登录标志 */
	private Boolean firstLoginFlag = Boolean.FALSE;
	/** 上次登录时间*/
	private String lastLoginTime = null;
	/** MAC地址*/
	private String macAddress = null;
	/** 是否填写密保问题*/
	private Boolean encryptedFlag = null;
	/** 是否填写个人信息*/
	private Boolean informationFlag = null;
	

	public Boolean getEncryptedFlag() {
		return encryptedFlag;
	}

	public void setEncryptedFlag(Boolean encryptedFlag) {
		this.encryptedFlag = encryptedFlag;
	}

	public Boolean getInformationFlag() {
		return informationFlag;
	}

	public void setInformationFlag(Boolean informationFlag) {
		this.informationFlag = informationFlag;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Boolean getFirstLoginFlag() {
		return firstLoginFlag;
	}

	public void setFirstLoginFlag(Boolean firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
}
