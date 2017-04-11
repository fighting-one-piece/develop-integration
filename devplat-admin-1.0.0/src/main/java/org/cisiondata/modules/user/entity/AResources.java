package org.cisiondata.modules.user.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/**
 * 资源实体类
 * @author 
 *
 */
public class AResources extends PKAutoEntity<Long>{

	
	private static final long serialVersionUID = 1L;
	/** 资源名称	*/
	private String name;
	/** 资源类型	*/
	private int type;
	/** 资源标识 */
	private String identity;
	/** 资源URL	*/
	private String url;
	/** 资源图标	*/
	private String icon;
	/** 资源优先级	*/
	private Integer priority;
	/**	父资源ID	*/
	private String parentId;
	/** 删除标识	*/
	private int deleteFlag;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
