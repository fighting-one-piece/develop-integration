package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户资源属性表 */
@Entity
@Table(name = "T_USER_RESOURCE_ATTRIBUTE")
public class AuthUserResourceAttribute extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户资源ID */
	@Column(name = "USER_RESOURCE_ID")
	private Long userResourceId = null;
	/** 属性KEY */
	@Column(name = "KEY")
	private String key = null;
	/** 属性VALUE */
	@Column(name = "VALUE")
	private String value = null;
	/** 属性类型 */
	@Column(name = "TYPE")
	private String type = null;
	/** 用户资源ID */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "USER_RESOURCE_ID")
	private AuthUserResource userResource = null;

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

	public Long getUserResourceId() {
		return userResourceId;
	}

	public void setUserResourceId(Long userResourceId) {
		this.userResourceId = userResourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AuthUserResource getUserResource() {
		return userResource;
	}

	public void setUserResource(AuthUserResource userResource) {
		this.userResource = userResource;
	}

}
