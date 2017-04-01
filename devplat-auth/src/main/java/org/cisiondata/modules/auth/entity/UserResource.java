package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户资源表*/
@Entity
@Table(name="T_USER_RESOURCE")
public class UserResource extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 计费*/
	public static final String CHARGINGS = "chargings";
	/** 域*/
	public static final String FIELDS = "fields";

	/** 用户ID */
	@Column(name="USER_ID")
	private Long userId = null;
	/** 资源ID */
	@Column(name="RESOURCE_ID")
	private Long resourceId = null;
	/** 优先权*/
	@Column(name="PRIORITY")
	private Integer priority = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = false;
	/** 用户 */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="USER_ID")
	private transient User user = null;
	/** 资源 */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="RESOURCE_ID")
	private transient Resource resource = null;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
