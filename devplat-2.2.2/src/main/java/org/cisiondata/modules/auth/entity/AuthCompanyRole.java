package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 单位角色表 */
@Entity
@Table(name = "T_COMPANY_ROLE")
public class AuthCompanyRole extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 单位ID */
	private Long companyId = null;
	/** 角色ID */
	private Long roleId = null;
	/** 优先权 */
	private String priority = null;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
