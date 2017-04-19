package org.cisiondata.modules.system.entity;

import java.io.Serializable;

public class ESMetadatas implements Serializable {

	/**
	 * 国际化资源实体类
	 */
	private static final long serialVersionUID = 1L;
	/*id*/
	private Long id = null;
	/*库*/
	private String indexs = null;
	/*类型*/
	private String type = null;
	/*中文*/
	private String attribute_en = null;
	/*英文*/
	private String attribute_ch = null;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	@Override
	public String toString() {
		return "ESMetadata [id=" + id + ", indexs=" + indexs + ", type=" + type + ", attribute_en=" + attribute_en
				+ ", attribute_ch=" + attribute_ch + "]";
	}
	
}
