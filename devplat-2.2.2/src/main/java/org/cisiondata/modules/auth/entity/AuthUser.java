package org.cisiondata.modules.auth.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;
import org.cisiondata.modules.auth.dao.utils.EntityAttributeUtils;

/** 用户表 */
@Entity
@Table(name = "T_USER")
public class AuthUser extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 密保问题 */
	public static final String QUESTION = "question";
	/** 密保答案 */
	public static final String ANSWER = "answer";
	/** 是否首次登录 */
	public static final String FIRST_LOGIN_FLAG = "firstLoginFlag";
	/** 是否填了密保 */
	public static final String ENCRYPTED_FLAG = "encryptedFlag";
	/** 是否填了个人信息 */
	public static final String INFORMATION_FLAG = "informationFlag";
	/** 是否填了IP地址 */
	public static final String IP_STATE = "ipstate";

	/** 用户账号 */
	@Column(name = "ACCOUNT")
	private String account = null;
	/** 用户密码 */
	@Column(name = "PASSWORD")
	private String password = null;
	/** 盐值 */
	@Column(name = "SALT")
	private String salt = null;
	/** 用户标识 */
	@Column(name = "IDENTITY")
	private String identity = null;
	/** 用户昵称 */
	@Column(name = "NICK_NAME")
	private String nickName = null;
	/** 用户真名 */
	@Column(name = "REAL_NAME")
	private String realName = null;
	/** 身份证号码 */
	@Column(name = "ID_CARD")
	private String idCard = null;
	/** 手机号码 */
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone = null;
	/** 用户邮箱 */
	@Column(name = "EMAIL")
	private String email = null;
	/** 用户状态 */
	@Column(name = "STATUS")
	private Integer status = null;
	/** 创建时间 */
	@Column(name = "CREATE_TIME")
	private Date createTime = null;
	/** 过期时间 */
	@Column(name = "EXPIRE_TIME")
	private Date expireTime = null;
	/** 是否删除标志 */
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag = false;
	/** 访问Token */
	private transient String accessToken = null;
	/** 密保问题 */
	private transient String question = null;
	/** 密保答案 */
	private transient String answer = null;
	/** 是否第一次登录 */
	private transient Boolean firstLoginFlag = true;
	/** 是否填写密保问题 */
	private transient Boolean encryptedFlag = false;
	/** 是否填写个人信息 */
	private transient Boolean informationFlag = false;
	/** 访问ID */
	private transient String accessId = null;
	/** 访问KEY */
	private transient String accessKey = null;
	/** 用户属性列表 */
	private List<AuthUserAttribute> attributes = null;
	/** 用户类型 */
	@Column(name = "GENRE")
	private Integer genre = null;
	/** 单位id */
	@Column(name = "COMPANY_ID")
	private Long companyId = null;
	/** 警官证号码 */
	private String policeIdCard = null;


	public String getPoliceIdCard() {
		return policeIdCard;
	}

	public void setPoliceIdCard(String policeIdCard) {
		this.policeIdCard = policeIdCard;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public List<AuthUserAttribute> getAttributes() {
		if (null == attributes)
			attributes = new ArrayList<AuthUserAttribute>();
		return attributes;
	}

	public void setAttributes(List<AuthUserAttribute> attributes) {
		this.attributes = attributes;
	}

	public boolean hasExpired() {
		return expireTime.before(Calendar.getInstance().getTime());
	}
	
	public boolean isValid() {
		return hasDeleted() || hasExpired() ? false : true;
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

	public void transientHandle() {
		EntityAttributeUtils.fillEntity(attributes, this);
	}

}
