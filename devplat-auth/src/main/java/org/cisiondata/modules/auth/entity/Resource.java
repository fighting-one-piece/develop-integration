package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源表*/
@Entity
@Table(name="T_RESOURCE")
public class Resource extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 根ID*/
	public static final Long ROOT = 1L;
	
	/** 计费*/
	public static final String CHARGINGS = "chargings";
	/** 域*/
	public static final String FIELDS = "fields";
	
	
	/** 资源类型*/
	@Column(name="TYPE")
	private Integer type = null;
	/** 资源名称*/
	@Column(name="NAME")
	private String name = null;
	/** 资源标识*/
	@Column(name="IDENTITY")
	private String identity = null;
	/** 资源URL*/
	@Column(name="URL")
	private String url = null;
	/**资源优先权*/
	@Column(name="PRIORITY")
	private Integer priority = null;
	/** 父资源ID */
	@Column(name = "PARENT_ID")
	private Long parentId = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	/** 资源图标*/
	private transient String icon = null;
	/** 父资源*/
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "PARENT_ID")
	private transient Resource parent = null;
	/** 是否有子节点*/
	private transient boolean hasChildren = false;
	/** 子资源*/
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
	private transient Set<Resource> children = null;
	/** 用户属性列表*/
	private transient List<UserAttribute> attributes = null;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public Set<Resource> getChildren() {
		if (children == null) {
			children = new HashSet<Resource>();
		}
		return children;
	}

	public void setChildren(Set<Resource> children) {
		this.children = children;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}
	
	public boolean hasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public boolean isTop() {
		return null != this.getParent() && Resource.ROOT.equals(this.getParent().getId());
	}
	
	public List<UserAttribute> getAttributes() {
		if (null == attributes) attributes = new ArrayList<UserAttribute>();
		return attributes;
	}

	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}

}

