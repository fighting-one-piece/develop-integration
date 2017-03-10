package org.cisiondata.modules.auth.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户账号 */
	private String account = null;
	/** 用户昵称*/
	private String nickname = null;
	/** 访问令牌*/
	private String accessToken = null;
	/** 是否首次登录标志 */
	private Boolean firstLoginFlag = Boolean.FALSE;
	
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

	
	
	
}
