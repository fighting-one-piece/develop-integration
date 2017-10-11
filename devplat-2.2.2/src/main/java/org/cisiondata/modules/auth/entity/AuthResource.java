package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源表 */
@Entity
@Table(name = "T_RESOURCE")
public class AuthResource extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 根ID */
	public static final Long ROOT = 1L;

	/** 计费 */
	public static final String CHARGINGS = "chargings";
	/** 域 */
	public static final String FIELDS = "fields";

	/** 资源类型 */
	@Column(name = "TYPE")
	private Integer type = null;
	/** 资源分类 */
	@Column(name = "CLASSIFY")
	private Integer classify = null;
	/** 资源名称 */
	@Column(name = "NAME")
	private String name = null;
	/** 资源标识 */
	@Column(name = "IDENTITY")
	private String identity = null;
	/** 资源URL */
	@Column(name = "URL")
	private String url = null;
	/** 资源优先权 */
	@Column(name = "PRIORITY")
	private Integer priority = null;
	/** 父资源ID */
	@Column(name = "PARENT_ID")
	private Long parentId = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	/** 资源图标 */
	private String icon = null;
	/** 用户属性列表 */
	private List<AuthResourceAttribute> attributes = null;
	/** 父ids */
	private Set<Long> parentIds = null;
	/** 类型：模块，菜单，接口 */
	private Integer genre = null;
	/** 子资源 */
	private List<AuthResource> children = null;

	public List<AuthResourceAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AuthResourceAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(AuthResourceAttribute attribute) {
		if (null == this.attributes) {
			List<AuthResourceAttribute> list = new ArrayList<AuthResourceAttribute>();
			list.add(attribute);
			this.attributes = list;
		} else {
			this.attributes.add(attribute);
		}
	}

	public Set<Long> getParentIds() {
		return parentIds;
	}

	public void setParentIds(Set<Long> parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public void addChild(AuthResource child) {
		if (null == this.children) {
			List<AuthResource> list = new ArrayList<AuthResource>();
			list.add(child);
			this.children = list;
		} else {
			this.children.add(child);
		}
	}

	public List<AuthResource> getChildren() {
		return children;
	}

	public void setChildren(List<AuthResource> children) {
		this.children = children;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
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
}
