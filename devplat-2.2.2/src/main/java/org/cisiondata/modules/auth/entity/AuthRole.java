package org.cisiondata.modules.auth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 角色表 */
@Entity
@Table(name = "T_ROLE")
public class AuthRole extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 角色名称 */
	@Column(name = "NAME")
	private String name = null;
	/** 角色标识 */
	@Column(name = "IDENTITY")
	private String identity = null;
	/** 角色描述 */
	@Column(name = "DESCRIPTION")
	private String desc = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	/** 用户角色集合 */
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<AuthUserRole> userRoles = null;
	/** 角色菜单集合 */
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AuthRoleResource> roleResources = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Set<AuthUserRole> getUserRoles() {
		if (null == userRoles) {
			userRoles = new HashSet<AuthUserRole>();
		}
		return this.userRoles;
	}

	public void setUserRoles(Set<AuthUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<AuthRoleResource> getRoleResources() {
		if (null == roleResources) {
			roleResources = new HashSet<AuthRoleResource>();
		}
		return this.roleResources;
	}

	public void setRoleResources(Set<AuthRoleResource> roleResources) {
		this.roleResources = roleResources;
	}

}
