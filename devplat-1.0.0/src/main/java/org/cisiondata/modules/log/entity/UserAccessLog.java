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
	
	/** IP地址 */
	private String ip = null;
	/** 用户账号 */
	private String account = null;
	/** 访问地址 */
	private String url = null;
	/** 访问时间 */
	private Date accessTime = null;
	/** 请求参数 */
	private String params = null;
	/*请求结果*/
	private String result = null;
	/**统计关键字*/
	private transient int count = 0;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
}
