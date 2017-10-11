package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户角色表 */
@Entity
@Table(name = "T_USER_ROLE")
public class AuthUserRole extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "USER_ID")
	private AuthUser user = null;
	/** 角色ID */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "ROLE_ID")
	private AuthRole role = null;
	/** 优先权 */
	@Column(name = "PRIORITY")
	private Integer priority = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = false;

	public AuthUser getUser() {
		return this.user;
	}

	public void setUser(AuthUser user) {
		this.user = user;
	}

	public AuthRole getRole() {
		return this.role;
	}

	public void setRole(AuthRole role) {
		this.role = role;
	}

	public Integer getPriority() {
		return this.priority;
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
