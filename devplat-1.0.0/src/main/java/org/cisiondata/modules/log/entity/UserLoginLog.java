package org.cisiondata.modules.log.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户登录日志 */
@Entity
@Table(name="T_USER_LOGIN_LOG")
public class UserLoginLog extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 登录用户账号 */
	private String account = null;
	/** 登入/登出IP*/
	private String ip = null;
	/** 登入/登出时间 */
	private Date currentTime = null;
	/** 登入/登出状态 */
	private int status = 0;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Date getCurrentTime() {
		return currentTime;
	}
	
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
}
