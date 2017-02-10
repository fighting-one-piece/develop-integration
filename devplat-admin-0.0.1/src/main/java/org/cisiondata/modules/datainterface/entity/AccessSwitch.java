package org.cisiondata.modules.datainterface.entity;

import java.io.Serializable;

public class AccessSwitch implements Serializable {

	/**
	 * 开关
	 */
	private static final long serialVersionUID = 1L;

	//id 自动增长
	private Integer id = null;
	//开关标识
	private String switch_identity = null;
	//开关名称
	private String switch_name = null;
	//开关描述
	private String swith_desc = null;
	//状态
	private Integer status =null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSwitch_identity() {
		return switch_identity;
	}
	public void setSwitch_identity(String switch_identity) {
		this.switch_identity = switch_identity;
	}
	public String getSwitch_name() {
		return switch_name;
	}
	public void setSwitch_name(String switch_name) {
		this.switch_name = switch_name;
	}
	public String getSwith_desc() {
		return swith_desc;
	}
	public void setSwith_desc(String swith_desc) {
		this.swith_desc = swith_desc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
