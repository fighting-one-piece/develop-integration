package org.cisiondata.modules.admin.statistics.entity;

import java.util.Date;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class DataRecord extends PKAutoEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 姓名 */
	private String name = null;
	/** 数据数 */
	private Integer dataNum = null;
	/** 文档数 */
	private Integer docNum = null;
	/** 日期 */
	private String insertDate = null;
	/** 时间 */
	private Date insertTime = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDataNum() {
		return dataNum;
	}

	public void setDataNum(Integer dataNum) {
		this.dataNum = dataNum;
	}

	public Integer getDocNum() {
		return docNum;
	}

	public void setDocNum(Integer docNum) {
		this.docNum = docNum;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	

}
