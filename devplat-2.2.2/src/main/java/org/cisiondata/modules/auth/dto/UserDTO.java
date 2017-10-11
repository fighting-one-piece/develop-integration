package org.cisiondata.modules.auth.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户账号 */
	private String account = null;
	/** 用户密码 */
	private String password = null;
	/** 盐值 */
	private String salt = null;
	/** 用户标识 */
	private String identity = null;
	/** 用户昵称 */
	private String nickname = null;
	/** 用户真名 */
	private String realname = null;
	/** 身份证号码 */
	private String idCard = null;
	/** 手机号码 */
	private String mobilePhone = null;
	/** 用户邮箱 */
	private String email = null;
	/** 用户状态 */
	private Integer status = null;
	/** 创建时间 */
	private Date createTime = null;
	/** 过期时间 */
	private Date expireTime = null;
	/** 是否删除标志 */
	private Boolean deleteFlag = false;
	/** 访问Token */
	private String accessToken = null;
	/** 密保问题 */
	private String question = null;
	/** 密保答案 */
	private String answer = null;
	/** 是否第一次登录 */
	private Boolean firstLoginFlag = true;
	/** 是否填写密保问题 */
	private transient Boolean encryptedFlag = false;
	/** 是否填写个人信息 */
	private transient Boolean informationFlag = false;
	/** 访问ID */
	private String accessId = null;
	/** 访问KEY */
	private String accessKey = null;

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

	public Boolean getEncryptedFlag() {
		return encryptedFlag;
	}

	public void setEncryptedFlag(Boolean encryptedFlag) {
		this.encryptedFlag = encryptedFlag;
	}

	public Boolean getInformationFlag() {
		return informationFlag;
	}

	public void setInformationFlag(Boolean informationFlag) {
		this.informationFlag = informationFlag;
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

}
