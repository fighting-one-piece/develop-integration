package org.cisiondata.modules.elasticsearch.entity;

import java.util.Date;

import org.cisiondata.modules.abstr.entity.PKAutoEntity;

public class LMData extends PKAutoEntity<Long>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//柠檬搜索关键字
	private String lmKey;
	//返回数据
    private String queryData;
    //查询时间
    private Date queryTime;

	public String getLmKey() {
		return lmKey;
	}

	public void setLmKey(String lmKey) {
		this.lmKey = lmKey;
	}

	public String getQueryData() {
		return queryData;
	}

	public void setQueryData(String queryData) {
		this.queryData = queryData;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
    
    
}