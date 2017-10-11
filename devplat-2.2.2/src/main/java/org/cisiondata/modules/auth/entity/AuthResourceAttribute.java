package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源属性表 */
@Entity
@Table(name = "T_RESOURCE_ATTRIBUTE")
public class AuthResourceAttribute extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 资源ID */
	@Column(name = "RESOURCE_ID")
	private Long resourceId = null;
	/** 属性KEY */
	@Column(name = "KEY")
	private String key = null;
	/** 属性VALUE */
	@Column(name = "VALUE")
	private String value = null;
	/** 属性类型 */
	@Column(name = "TYPE")
	private String type = null;
	/** 资源 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "RESOURCE_ID")
	private transient AuthResource resource = null;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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

	public AuthResource getResource() {
		return resource;
	}

	public void setResource(AuthResource resource) {
		this.resource = resource;
	}

}
