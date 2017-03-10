package org.cisiondata.modules.log.entity;

import java.util.Date;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/**
 * 用户收藏实体类
 * @author fb
 *
 */
public class UserCollect extends PKAutoEntity<Long>{

	private static final long serialVersionUID = 1L;
	/**用户名*/
	private String account = null;
	/**收藏类型*/
	private String type = null;
	/**收藏时间*/
	private Date collectTime = null;
	/**收藏内容*/
	private String collectContent = null;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getCollectContent() {
		return collectContent;
	}
	public void setCollectContent(String collectContent) {
		this.collectContent = collectContent;
	}
	
}
