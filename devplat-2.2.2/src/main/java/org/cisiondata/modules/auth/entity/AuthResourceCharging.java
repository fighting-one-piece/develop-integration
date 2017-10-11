package org.cisiondata.modules.auth.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 资源计费 */
@Entity
public class AuthResourceCharging extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 资源ID */
	private Long resourceId = null;
	/** 资源返回结果代码 */
	private Integer resultCode = null;
	/** 资源收费类型 */
	private Integer charingType = null;
	/** 费用 */
	private Double cost = null;
	/** 过期时间 */
	private Date expireTime = null;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public Integer getCharingType() {
		return charingType;
	}

	public void setCharingType(Integer charingType) {
		this.charingType = charingType;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}
