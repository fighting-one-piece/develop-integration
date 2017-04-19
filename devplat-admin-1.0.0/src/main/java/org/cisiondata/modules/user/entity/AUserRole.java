package org.cisiondata.modules.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name="T_USER_ROLE")
public class AUserRole extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**用户ID */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="USER_ID")
	private Long auserId = null;
	/**角色ID */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="ROLE_ID")
	private Long aroleId = null;
	/**优先权*/
	@Column(name="PRIORITY")
	private String priority = null;
	
	
	 
	public Long getAuserId() {
		return auserId;
	}
	public void setAuserId(Long auserId) {
		this.auserId = auserId;
	}
	public Long getAroleId() {
		return aroleId;
	}
	public void setAroleId(Long aroleId) {
		this.aroleId = aroleId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	
}
