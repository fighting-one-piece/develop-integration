package org.cisiondata.modules.search.entity;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class ESMetadata extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String indexs;
	private String type;
	private String attribute_en;
	private String attribute_ch;
	
	public String getIndexs() {
		return indexs;
	}
	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAttribute_en() {
		return attribute_en;
	}
	public void setAttribute_en(String attribute_en) {
		this.attribute_en = attribute_en;
	}
	public String getAttribute_ch() {
		return attribute_ch;
	}
	public void setAttribute_ch(String attribute_ch) {
		this.attribute_ch = attribute_ch;
	}
}
