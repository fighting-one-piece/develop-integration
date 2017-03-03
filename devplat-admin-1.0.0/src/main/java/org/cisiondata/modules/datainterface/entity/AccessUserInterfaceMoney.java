package org.cisiondata.modules.datainterface.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class AccessUserInterfaceMoney extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 用户接口ID */
	private Long userInterfaceId = null;
	/** 响应代码 */
	private int responseCode = 0;
	/** 金额 */
	private Double money = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;

	public Long getUserInterfaceId() {
		return userInterfaceId;
	}

	public void setUserInterfaceId(Long userInterfaceId) {
		this.userInterfaceId = userInterfaceId;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
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
