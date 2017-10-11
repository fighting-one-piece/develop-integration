package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.List;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源表 */
public class AuthResourceMenu extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 菜单名称 */
	private String name = null;
	/** 子菜单 */
	private List<AuthResourceMenu> children = null;
	/** 父资源ID */
	private Long parentId = null;
	/** 菜单是否显示 */
	private boolean show = Boolean.FALSE;

	public void addChild(AuthResourceMenu child) {
		if (null == this.children) {
			List<AuthResourceMenu> list = new ArrayList<AuthResourceMenu>();
			list.add(child);
			this.children = list;
		} else {
			this.children.add(child);
		}
	}

	public List<AuthResourceMenu> getChildren() {
		return children;
	}

	public void setChildren(List<AuthResourceMenu> children) {
		this.children = children;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
