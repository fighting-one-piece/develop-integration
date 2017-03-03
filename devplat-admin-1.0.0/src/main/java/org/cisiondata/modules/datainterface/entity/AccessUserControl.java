package org.cisiondata.modules.datainterface.entity;

import java.io.Serializable;

public class AccessUserControl implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String account = null;
	
	private Long count = null;
	
	private Long remainingCount = null;
	
	private String deleteFlag = null;
	//总金额
	private Double money = null;
	//剩余金额
	private Double remainingMoney = null;
	//账户类型 1代表普通用户，2代表接口调用方
	private Integer source = null;
	//查询次数
	private Long queryCount = null;

	public Long getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(Long queryCount) {
		this.queryCount = queryCount;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getRemainingMoney() {
		return remainingMoney;
	}

	public void setRemainingMoney(Double remainingMoney) {
		this.remainingMoney = remainingMoney;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
