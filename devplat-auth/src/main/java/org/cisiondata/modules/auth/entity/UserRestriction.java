package org.cisiondata.modules.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户限制表*/
@Entity
@Table(name="T_USER_RESTRICTION")
public class UserRestriction extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户ID*/
	@ManyToOne(cascade=CascadeType.REFRESH, optional=true)
	@JoinColumn(name="USER_ID")
	private User user = null;
	/** 限制类型*/
	@Column(name="RESTRICT_TYPE")
	private Integer restrictType = null;
	/** 限制次数*/
	@Column(name="RESTRICT_COUNT")
	private Integer restrictCount = null;
	
}
