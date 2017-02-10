package org.cisiondata.modules.datainterface.entity;

import java.io.Serializable;
import java.util.Date;

public class AccessUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String accessId = null;
	
	private String accessKey = null;
	
	private String name = null;
	
	private Date applyTime = null;
	
	private Integer deleteFlag = null;
	
	private AccessUserControl accessControl = null;
	
	public AccessUserControl getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(AccessUserControl accessControl) {
		this.accessControl = accessControl;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}


}
