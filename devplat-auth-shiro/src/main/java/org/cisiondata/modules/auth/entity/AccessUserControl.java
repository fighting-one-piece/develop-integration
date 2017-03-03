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
	/** 查询返回总条数 */
	@Column(name = "COUNT")
	private Long count = null;
	/** 剩余查询返回总条数 */
	@Column(name = "REMAINING_COUNT")
	private Long remainingCount = null;
	/** 查询次数 */
	@Column(name = "QUERY_COUNT")
	private Long queryCount = null;
	/** 查询返回金额 */
	@Column(name = "MONEY")
	private Double money = null;
	/** 剩余查询返回金额 */
	@Column(name = "REMAINING_MONEY")
	private Double remainingMoney = null;
	/** 删除标记 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = null;

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
