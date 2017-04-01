package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源接口域*/
@Entity
public class ResourceInterfaceField extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 资源ID */
	private Long resourceId = null;
	/** 接口域英文 */
	private String fieldEN = null;
	/** 接口域中文 */
	private String fieldCH = null;
	/** 是否默认*/
	private Boolean isDefault = false;
	/** 是否链接*/
	private Boolean isLink = false;
	/** 是否链接地图*/
	private Boolean isLinkMap = false;
	/** 加密类型*/
	private Integer encryptType = null;
	
	public Long getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getFieldEN() {
		return fieldEN;
	}
	
	public void setFieldEN(String fieldEN) {
		this.fieldEN = fieldEN;
	}
	
	public String getFieldCH() {
		return fieldCH;
	}
	
	public void setFieldCH(String fieldCH) {
		this.fieldCH = fieldCH;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public Boolean getIsLink() {
		return isLink;
	}
	
	public void setIsLink(Boolean isLink) {
		this.isLink = isLink;
	}
	
	public Boolean getIsLinkMap() {
		return isLinkMap;
	}

	public void setIsLinkMap(Boolean isLinkMap) {
		this.isLinkMap = isLinkMap;
	}

	public Integer getEncryptType() {
		return encryptType;
	}
	
	public void setEncryptType(Integer encryptType) {
		this.encryptType = encryptType;
	}
	
}
