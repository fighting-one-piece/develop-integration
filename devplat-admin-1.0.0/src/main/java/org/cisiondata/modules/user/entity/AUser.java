package org.cisiondata.modules.user.entity;

import java.util.Date;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

//用户实体类
public class AUser extends PKAutoEntity<Long> {

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
	/** MAC地址 */
	private String macAddress = null;
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
	/** 是否拥有当前角色*/
	private transient boolean check = false;

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

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

}
