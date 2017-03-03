package org.cisiondata.modules.system.entity;

/**
 * 敏感词实体类
 * @author Administrator
 *
 */
public class SensitiveWord {
	
	private static final long serialVersionUID = 1L;
	
	//事件ID
	private Long id=null;
	//敏感词
	private String word=null;
	//类型
	private String type=null;
	//标识
	private Boolean identity=null;
	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIdentity() {
		return identity;
	}
	public void setIdentity(Boolean identity) {
		this.identity = identity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
