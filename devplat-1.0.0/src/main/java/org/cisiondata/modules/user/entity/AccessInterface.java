package org.cisiondata.modules.user.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;
/**
 * 访问接口
 *
 */
public class AccessInterface extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 访问接口URL*/
	private String url = null;
	/** 访问接口标识 */
	private String identity = null;
	/** 访问接口金额 */
	private Double money = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;
	/** 接口参数，JSON字符串*/
	private String parameters = null;
	/** 接口返回的所有字段，多个字段名用逗号隔开*/
	private String fields = null;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean hasDeleted() {
		return deleteFlag;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

}
