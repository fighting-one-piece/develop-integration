package org.cisiondata.modules.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name="T_SECURITY")
public class Security extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 密保问题 */
	@Column(name="SECURITY_QUESTION")
	private String securityQuestion = null;
	/** 删除标示 */
	@Column(name="DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	

}
