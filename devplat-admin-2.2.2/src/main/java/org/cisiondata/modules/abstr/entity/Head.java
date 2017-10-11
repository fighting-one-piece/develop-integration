package org.cisiondata.modules.abstr.entity;

import java.io.Serializable;

public class Head implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 属性*/
	public String field = null;
	/** 属性中文名*/
	public String fieldName = null;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	
}
