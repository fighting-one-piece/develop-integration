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
	private Long auser = null;
	/**角色ID */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="ROLE_ID")
	private Long arole = null;
	/**优先权*/
	@Column(name="PRIORITY")
	private String priority = null;
	
	
	 
	public Long getAuser() {
		return auser;
	}
	public void setAuser(Long auser) {
		this.auser = auser;
	}
	public Long getArole() {
		return arole;
	}
	public void setArole(Long arole) {
		this.arole = arole;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	
}
