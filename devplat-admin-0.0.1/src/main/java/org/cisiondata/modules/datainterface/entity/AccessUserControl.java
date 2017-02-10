package org.cisiondata.modules.datainterface.entity;

import java.io.Serializable;

public class AccessUserControl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String account = null;
	
	private String interfaceUrl = null;
	
	private Long count = null;
	
	private Long remainingCount = null;
	
	private String deleteFlag = null;

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

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	

}
