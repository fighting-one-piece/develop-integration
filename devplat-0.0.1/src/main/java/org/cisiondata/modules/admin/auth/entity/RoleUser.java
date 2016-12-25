package org.cisiondata.modules.admin.auth.entity;

/**
 *角色管理实体类
 * @author Administrator
 *
 */
public class RoleUser {

	/**id*/
	private Long Roleid=null;
	/** 角色名称 */
	private String name = null;
	/** 角色标识 */
	private String identity = null;
	/** 角色描述 */
	private String desc = null;
	/** 是否删除标志 */
	private Boolean deleteFlag =null;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getRoleid() {
		return Roleid;
	}

	public void setRoleid(Long roleid) {
		Roleid = roleid;
	}


}
