package org.cisiondata.modules.user.entity;

import java.util.List;

import org.cisiondata.modules.auth.entity.ResourceCharging;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;

public class AResource extends org.cisiondata.modules.auth.entity.Resource {

	private static final long serialVersionUID = 1L;
	private Boolean check = false;
	/** 字段以及处理方式 */
	private List<ResourceInterfaceField> fields = null;
	/** 收费 */
	private List<ResourceCharging> chargings = null;

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public List<ResourceInterfaceField> getFields() {
		return fields;
	}

	public void setFields(List<ResourceInterfaceField> fields) {
		this.fields = fields;
	}

	public List<ResourceCharging> getChargings() {
		return chargings;
	}

	public void setChargings(List<ResourceCharging> chargings) {
		this.chargings = chargings;
	}

}
