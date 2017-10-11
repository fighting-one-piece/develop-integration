package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 角色资源属性表 */
@Entity
@Table(name = "T_ROLE_RESOURCE_ATTRIBUTE")
public class AuthRoleResourceAttribute extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 角色资源ID */
	private Long roleResourceId = null;
	/** 属性KEY */
	private String key = null;
	/** 属性VALUE */
	private String value = null;
	/** 属性类型 */
	private String type = null;

	public Long getRoleResourceId() {
		return roleResourceId;
	}

	public void setRoleResourceId(Long roleResourceId) {
		this.roleResourceId = roleResourceId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
