package org.cisiondata.modules.datainterface.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class AccessUserInterface extends PKAutoEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	/** 账户 */
	private String account = null;
	/** 访问接口ID */
	private Long interfaceId = null;
	/* 是否收费标志 */
	private Boolean chargeFlag = Boolean.TRUE;
	/** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;
	/*接口表ID*/
	
	private  AccessInterface inter_id;
	
	
	public AccessInterface getInter_id() {
		return inter_id;
	}
	public void setInter_id(AccessInterface inter_id) {
		this.inter_id = inter_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}
	public Boolean getChargeFlag() {
		return chargeFlag;
	}
	public void setChargeFlag(Boolean chargeFlag) {
		this.chargeFlag = chargeFlag;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	

}
