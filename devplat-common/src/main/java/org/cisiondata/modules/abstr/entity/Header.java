package org.cisiondata.modules.abstr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Header implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 域英文名称*/
	private String fieldEN = null;
	/** 域中文名称*/
	private String fieldCH = null;
	/** 子域*/
	private List<Header> children = null;
	
	public String getFieldEN() {
		return fieldEN;
	}
	
	public void setFieldEN(String fieldEN) {
		this.fieldEN = fieldEN;
	}
	
	public String getFieldCH() {
		return fieldCH;
	}
	
	public void setFieldCH(String fieldCH) {
		this.fieldCH = fieldCH;
	}
	
	public List<Header> getChildren() {
		if (null == children) children = new ArrayList<Header>();
		return children;
	}
	
	public void setChildren(List<Header> children) {
		this.children = children;
	}
	
}
