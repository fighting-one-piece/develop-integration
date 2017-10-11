package org.cisiondata.modules.auth.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;
@Entity
@Table(name="T_COMPANY")
public class AuthCompany extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**单位名称*/
	private String companyName = null;
	/**单位联系人*/
    private String companyContact = null;
    /**单位邮箱*/
    private String companyEmail = null;
    /**单位联系电话*/
    private String companyTelephone = null;
    /**单位地址*/
    private String companyAddress = null;
    /** 是否删除标志 */
	private Boolean deleteFlag = Boolean.FALSE;
	/**所在省市*/
    private String companyProvince = null;
    /**过期时间*/
    private Date expireTime = null;

    public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact == null ? null : companyContact.trim();
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail == null ? null : companyEmail.trim();
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone == null ? null : companyTelephone.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}