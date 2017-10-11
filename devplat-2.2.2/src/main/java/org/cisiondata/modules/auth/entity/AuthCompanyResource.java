package org.cisiondata.modules.auth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 单位资源表 */
@Entity
@Table(name = "T_COMPANY_RESOURCE")
public class AuthCompanyResource extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 计费 */
	public static final String CHARGINGS = "chargings";
	/** 域 */
	public static final String FIELDS = "fields";

	/** 单位ID */
	private Long companyId = null;
	/** 资源ID */
	private Long resourceId = null;
	/** 优先权 */
	private Integer priority = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = false;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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
