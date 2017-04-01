package org.cisiondata.modules.user.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;
/**
 * 用户访问接口
 */
public class AccessUserInterface extends PKAutoEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	/** 账户 */
	private String account = null;
	/** 访问接口ID */
	private Long interfaceId = null;
	/** 是否收费标志 */
	private Boolean chargeFlag = Boolean.TRUE;
	/** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;
	/** 该用户返回的字段，以及每个字段的处理方式(不做操作、加密、可链接)
	 * 	存入json格式字符串
	 * */
	private String fields = null;
	
	
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
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
