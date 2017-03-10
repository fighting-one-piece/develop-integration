package org.cisiondata.modules.user.entity;

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
	/** 加密字段*/
	@Column(name="SALT")
	private String salt = null;
	/** 姓名*/
	@Column(name="REAL_NAME")
	private String realName = null;
	/** 身份证*/
	@Column(name="ID_CARD")
	private String idCard = null;
	/** 手机号码*/
	@Column(name="MOBILE_PHONE")
	private String mobilePhone = null;
	/** 密保问题*/
	@Column(name="QUESTION")
	private String question = null;
	/** 密保答案*/
	@Column(name="ANSWER")
	private String answer = null;
	/** 物理地址*/
	@Column(name="MAC_ADDRESS")
	private String macAddress = null;
	/** 是否第一次登陆标示*/
	@Column(name="FIRST_LOGIN_FLAG")
	private Boolean firstLoginFlag = Boolean.TRUE;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getidCard() {
		return idCard;
	}

	public void setidCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Boolean getFirstLoginFlag() {
		return firstLoginFlag;
	}

	public void setFirstLoginFlag(Boolean firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}

	
}
