package org.cisiondata.modules.admin.auth.entity;

import java.util.List;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/**
 * 超链接实体类
 * @author tjl
 *
 */
public class AResource extends PKAutoEntity<Long>{

	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private int type;
	
	private String identity;
	
	private String url;
	
	private String icon;
	
	private String priority;
	
	private String parentId;
	
	private int deleteFlag;

	private String parentName;
	/*下一级资源*/
	private List<AResource> children;
	
	public List<AResource> getChildren() {
		return children;
	}

	public void setChildren(List<AResource> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
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
