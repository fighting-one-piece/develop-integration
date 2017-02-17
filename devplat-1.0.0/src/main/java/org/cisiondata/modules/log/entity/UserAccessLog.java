package org.cisiondata.modules.log.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户访问操作日志 */
@Entity
@Table(name="T_USER_LOGIN_LOG")
public class UserAccessLog extends PKAutoEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	/** 会话ID */
	private String sessionId = null;
	/** IP地址 */
	private String ip = null;
	/** 用户账号 */
	private String account = null;
	/** 访问地址 */
	private String url = null;
	/** 访问时间 */
	private Date accessTime = null;
	/** 访问关键字/参数 */
	private String keyword = null;
	/** 统计 */
	private transient int count = 0;
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getAccessTime() {
		return accessTime;
	}
	
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
