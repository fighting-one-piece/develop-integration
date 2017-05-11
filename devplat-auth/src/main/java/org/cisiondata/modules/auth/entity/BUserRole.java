package org.cisiondata.modules.auth.entity;

import javax.persistence.JoinColumn;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户角色表*/
public class BUserRole extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**用户ID */
	private Long userId = null;
	/**角色ID */
	@JoinColumn(name="ROLE_ID")
	private Long roleId = null;
	/**优先权*/
	private Integer priority = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = false;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	
	

}
