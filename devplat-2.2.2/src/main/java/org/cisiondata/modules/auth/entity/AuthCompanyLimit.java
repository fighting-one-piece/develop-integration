package org.cisiondata.modules.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;


/**
 * 
 * @author 11064
 *
 */
@Entity
@Table(name = "T_COMPANY_LIMIT")
public class AuthCompanyLimit extends PKAutoEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COMPANY_ID")
	private Long companyId = null;

	@Column(name = "CT_COUNT")
	private Long ctCount = null;

	@Column(name = "TYPE")
	private Integer type = null;

	@Column(name = "RESOURCE_ID")
	private Long resourceId = null;

	@Column(name = "EXPIRE_TIME")
	private Date expireTime = null;

	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = false;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCtCount() {
		return ctCount;
	}

	public void setCtCount(Long ctCount) {
		this.ctCount = ctCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
