package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 角色限制表 */
@Entity
@Table(name = "T_ROLE_LIMIT")
public class AuthRoleLimit extends PKAutoEntity<Long> {
	private static final long serialVersionUID = 1L;
	/** 单位ID */
	private Long roleId = null;
	/** 限制类型 */
	private String limitType = null;
	/** 每日查询关键字上限 */
	private Long ctCount = null;
	/** 每日查询数据量上限 */
	private Long ctAccess = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = false;

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType == null ? null : limitType.trim();
	}

	public Long getCtCount() {
		return ctCount;
	}

	public void setCtCount(Long ctCount) {
		this.ctCount = ctCount;
	}

	public Long getCtAccess() {
		return ctAccess;
	}

	public void setCtAccess(Long ctAccess) {
		this.ctAccess = ctAccess;
	}

}