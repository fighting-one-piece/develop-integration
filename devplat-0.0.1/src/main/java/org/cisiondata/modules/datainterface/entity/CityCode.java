package org.cisiondata.modules.datainterface.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class CityCode extends PKAutoEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	//城市代码
	private String code;
	//城市名称
	private String city;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
