package org.cisiondata.modules.user.entity;
/**
 * 用户资源关系实体类
 * @author Administrator
 *
 */
public class AUserResource {
	/**	用户ID	*/
	private String userId = null;
	/**	用户账号	*/
	private String account = null;
	/**	资源ID	*/
	private String resourceId = null;
	/**	资源名称	*/
	private String resourceName = null;
	/**	删除标识*/
	private Boolean deleteFlag = null;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
