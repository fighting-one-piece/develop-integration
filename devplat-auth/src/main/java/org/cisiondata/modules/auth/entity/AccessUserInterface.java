package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name = "T_ACCESS_USER_INTERFACE")
public class AccessUserInterface extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 账户 */
	@Column(name = "ACCOUNT")
	private String account = null;
	/** 访问接口ID */
	@Column(name = "INTERFACE_ID")
	private Long interfaceId = null;
	/** 是否收费标志 */
	@Column(name = "CHARGE_FLAG")
	private Boolean chargeFlag = Boolean.TRUE;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	/** 用户接口金额列表 */
	private List<AccessUserInterfaceMoney> monies = null;

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

	public boolean hasDeleted() {
		return deleteFlag;
	}

	public List<AccessUserInterfaceMoney> getMonies() {
		if (null == monies) monies = new ArrayList<AccessUserInterfaceMoney>();
		return monies;
	}

	public void setMonies(List<AccessUserInterfaceMoney> monies) {
		this.monies = monies;
	}

}
