package org.cisiondata.modules.log.entity;

import java.util.Date;
/**
 * 日志实体类
 * @author fb
 *
 */
public class LogModel {
	
	//id
	private String id = null;
	//session的id
	private String sessionId = null;
	//ip地址
	private String ip = null;
	//访问用户
	private String account = null;
	//访问地址
	private String url = null;
	//访问时间
	private Date accessTime = null;
	//关键字
	private String keyword = null;
	//统计
	private String count = null;
	
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
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
	
}
