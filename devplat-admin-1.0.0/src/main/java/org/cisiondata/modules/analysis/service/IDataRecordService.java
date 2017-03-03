package org.cisiondata.modules.analysis.service;

import java.util.List;

import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.analysis.entity.DataRecord;
import org.cisiondata.utils.exception.BusinessException;

public interface IDataRecordService extends IGenericService<DataRecord, Long> {

	public List<DataRecord> insertAndReadList(String name, int dataNum, 
			int docNum) throws BusinessException; 
	
	public void insertDocNumAndDataNum(int day) throws BusinessException;
	
	public List<DataRecord> readData(String now);
	
	public List<DataRecord> readDataSeven(String time7);
	
	public List<DataRecord> readDataThirty(String time30);
}
