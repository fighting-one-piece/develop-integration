package org.cisiondata.modules.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name = "T_ACCESS_INTERFACE")
public class AccessInterface extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 访问接口URL */
	@Column(name = "URL")
	private String url = null;
	/** 访问接口标识 */
	@Column(name = "IDENTITY")
	private String identity = null;
	/** 访问接口金额 */
	@Column(name = "MONEY")
	private Double money = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
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
