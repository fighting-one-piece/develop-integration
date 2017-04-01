package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

/** 用户表*/
@Entity
@Table(name="T_USER")
public class User extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/** 密保问题*/
	public static final String QUESTION = "question";
	/** 密保答案*/
	public static final String ANSWER = "answer";
	/** 是否首次登录*/
	public static final String FIRST_LOGIN_FLAG = "firstLoginFlag";
	

	/** 用户账号 */
	@Column(name="ACCOUNT")
	private String account = null;
	/** 用户密码 */
	@Column(name="PASSWORD")
	private String password = null;
	/** 盐值*/
	@Column(name="SALT")
	private String salt = null;
	/** 用户标识 */
	@Column(name="IDENTITY")
	private String identity = null;
	/** 用户昵称*/
	@Column(name="NICK_NAME")
	private String nickname = null;
	/** 用户真名*/
	@Column(name="REAL_NAME")
	private String realname = null;
	/** 身份证号码*/
	@Column(name="ID_CARD")
	private String idCard = null;
	/** 手机号码*/
	@Column(name="MOBILE_PHONE")
	private String mobilePhone = null;
	/** MAC地址*/
	@Column(name="MAC_ADDRESS")
	private String macAddress = null;
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
	private Boolean deleteFlag = false;
	/** 访问Token*/
	private transient String accessToken = null;
	/** 密保问题*/
	private transient String question = null;
	/** 密保答案*/
	private transient String answer = null;
	/** 是否第一次登录*/
	private transient Boolean firstLoginFlag = true;
	/** 访问ID */
	private transient String accessId = null;
	/** 访问KEY */
	private transient String accessKey = null;
	/** 用户属性列表*/
	private transient List<UserAttribute> attributes = null;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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
	
	public boolean hasDeleted() {
		return deleteFlag;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	public Boolean getFirstLoginFlag() {
		return firstLoginFlag;
	}

	public void setFirstLoginFlag(Boolean firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public List<UserAttribute> getAttributes() {
		if (null == attributes) attributes = new ArrayList<UserAttribute>();
		return attributes;
	}

	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
	}

	public boolean hasExpired() {
		return expireTime.before(Calendar.getInstance().getTime());
	}
	
	public boolean isValid() {
		return hasDeleted() || hasExpired() ? false : true;
	}
	
}
