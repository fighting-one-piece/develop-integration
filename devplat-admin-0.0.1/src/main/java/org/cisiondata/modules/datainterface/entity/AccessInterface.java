package org.cisiondata.modules.datainterface.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class AccessInterface extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 访问接口URL */
	private String url = null;
	/** 访问接口标识 */
	private String identity = null;
	/** 访问接口金额 */
	private Double money = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean hasDeleted() {
		return deleteFlag;
	}

}
