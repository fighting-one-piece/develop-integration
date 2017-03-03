package org.cisiondata.modules.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name="T_USER_GROUP")
public class AUserAGroup extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="USER_ID")
	private Long auser = null;
	/** 组ID */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="GROUP_ID")
	private Long agroup = null;
	/** 优先权*/
	@Column(name="PRIORITY")
	private String priority = null;
	
	
	public Long getAuser() {
		return auser;
	}
	public void setAuser(Long auser) {
		this.auser = auser;
	}
	public Long getAgroup() {
		return agroup;
	}
	public void setAgroup(Long agroup) {
		this.agroup = agroup;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	
}
