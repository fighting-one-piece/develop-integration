package org.cisiondata.modules.admin.statistics.dao;

import java.util.List;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.admin.statistics.entity.DataRecord;
import org.springframework.stereotype.Repository;

@Repository("dataRecordDAO")
public interface DataRecordDAO extends GenericDAO<DataRecord, Long> {
	
	public List<DataRecord> readDataByInsertDate(String time);
	
	public List<DataRecord> readDataByTimeSeven(String time7);
	
	public List<DataRecord> readDataByTimeThirty(String time30);
}
