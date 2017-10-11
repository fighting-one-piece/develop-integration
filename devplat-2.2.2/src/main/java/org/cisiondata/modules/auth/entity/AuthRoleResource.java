package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 角色资源表 */
@Entity
@Table(name = "T_ROLE_RESOURCE")
public class AuthRoleResource extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long roleId = null;
	/** 资源ID */
	private Long resourceId = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = false;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
