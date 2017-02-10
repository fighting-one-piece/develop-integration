package org.cisiondata.modules.identity.entity;
/**
 * 手机号码归属地
 * @author fb
 * 
 *
 */
public class MobileAttributionModel {
	/*省份*/
	private String province;
	/*城市*/
	private String city;
	/*号段*/
	private String dnseg;
	/*手机号码归属*/
	private String regionDnseg;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDnseg() {
		return dnseg;
	}
	public void setDnseg(String dnseg) {
		this.dnseg = dnseg;
	}
	public String getRegionDnseg() {
		return regionDnseg;
	}
	public void setRegionDnseg(String regionDnseg) {
		this.regionDnseg = regionDnseg;
	}
	
}
