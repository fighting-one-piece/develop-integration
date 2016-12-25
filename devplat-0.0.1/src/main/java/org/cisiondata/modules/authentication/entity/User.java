package org.cisiondata.modules.authentication.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

@Entity
@Table(name="T_USER")
public class User extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 用户账号 */
	@Column(name="ACCOUNT")
	private String account = null;
	/** 用户密码 */
	@Column(name="PASSWORD")
	private String password = null;
	/** 用户标识 */
	@Column(name="IDENTITY")
	private String identity = null;
	/** 用户昵称*/
	@Column(name="NICK_NAME")
	private String nickname = null;
	/** 用户邮箱*/
	@Column(name="EMAIL")
	private String email = null;
	/** 用户状态*/
	@Column(name="STATUS")
	private Integer status = null;
	/** 创建时间*/
	@Column(name="CREATE_TIME")
	private Date createTime = null;
	/** 过期时间*/
	@Column(name="EXPIRE_TIME")
	private Date expireTime = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = Boolean.FALSE;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean hasAvailable() {
		return !deleteFlag;
	}

}
