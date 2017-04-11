package org.cisiondata.modules.user.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class APermission extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	/** 用户类型*/
	public static final Integer PRINCIPAL_TYPE_USER = 0;
	/** 角色类型*/
	public static final Integer PRINCIPAL_TYPE_ROLE = 1;
	
	
	/** 授权状态 用后四位标识CRUD操作*/
	private Integer authStatus;
	/** 继承状态  0标识不继承 1标识继承*/
	private Integer extendStatus;
	/** 主体标识*/
	private Long principalId;
	/** 主体类型  0标识用户   1标识角色*/
	private int principalType;
	/** 资源ID*/
	private Long resourceId;
	public Integer getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public Integer getExtendStatus() {
		return extendStatus;
	}
	public void setExtendStatus(Integer extendStatus) {
		this.extendStatus = extendStatus;
	}
	public Long getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}
	public int getPrincipalType() {
		return principalType;
	}
	public void setPrincipalType(int principalType) {
		this.principalType = principalType;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	

}
