package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户属性表 */
@Entity
@Table(name = "T_USER_ATTRIBUTE")
public class AuthUserAttribute extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Column(name = "USER_ID")
	private Long userId = null;
	/** 属性KEY */
	@Column(name = "KEY")
	private String key = null;
	/** 属性VALUE */
	@Column(name = "VALUE")
	private String value = null;
	/** 属性类型 */
	@Column(name = "TYPE")
	private String type = null;
	/** 用户 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "USER_ID")
	private transient AuthUser user = null;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public AuthUser getUser() {
		return user;
	}

	public void setUser(AuthUser user) {
		this.user = user;
	}

}
