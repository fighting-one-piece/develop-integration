package org.cisiondata.modules.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "T_ACCESS_USER_CONTROL")
public class AccessUserControl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 账户 */
	@Column(name = "ACCOUNT")
	private String account = null;
	
	/** 接口URL */
	@Column(name = "INTERFACE_URL")
	private String interfaceUrl = null;
	
	/** 查询返回条数 */
	@Column(name = "COUNT")
	private Long count = null;
	
	/** 剩余查询返回条数 */
	@Column(name = "REMAINING_COUNT")
	private Long remainingCount = null;
	
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = null;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getRemainingCount() {
		return remainingCount;
	}

	public void setRemainingCount(Long remainingCount) {
		this.remainingCount = remainingCount;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public boolean hasDeleted() {
		return this.deleteFlag;
	}

}
