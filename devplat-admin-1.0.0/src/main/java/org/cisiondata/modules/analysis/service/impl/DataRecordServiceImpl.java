package org.cisiondata.modules.analysis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.entity.Query;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.analysis.dao.DataRecordDAO;
import org.cisiondata.modules.analysis.entity.DataRecord;
import org.cisiondata.modules.analysis.service.IDataRecordService;
import org.cisiondata.utils.date.DateFormatter;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("dataRecordService")
public class DataRecordServiceImpl extends GenericServiceImpl<DataRecord, Long> 
	implements IDataRecordService {
	
	@Resource(name = "dataRecordDAO")
	private DataRecordDAO dataRecordDAO = null;

	@Override
	public GenericDAO<DataRecord, Long> obtainDAOInstance() {
		return dataRecordDAO;
	}
	
	@Override
	public List<DataRecord> insertAndReadList(String name, int dataNum,
			int docNum) throws BusinessException {
		String date = DateFormatter.DATE.get().format(new Date());
		Query query = new Query();
		query.addCondition("name", name);
		query.addCondition("insertDate", date);
		DataRecord dataRecord = dataRecordDAO.readDataByCondition(query);
		if (null != dataRecord) {
			throw new BusinessException("当天记录已经存在,无法继续添加");
		}
		dataRecord = new DataRecord();
		dataRecord.setName(name);
		dataRecord.setDataNum(dataNum);
		dataRecord.setDocNum(docNum);
		dataRecord.setInsertDate(date);
		dataRecord.setInsertTime(new Date());
		dataRecordDAO.insert(dataRecord);
		Query topQuery = new Query();
		topQuery.addOrderCondition("insert_time", Query.ORDER_DESC);
		return dataRecordDAO.readDataListByCondition(topQuery);
	}
	
	@Override
	public void insertDocNumAndDataNum(int day) throws BusinessException {
	}
	
	@Override
	public List<DataRecord> readData(String now) {
		List<DataRecord> list = new ArrayList<DataRecord>();
		list = dataRecordDAO.readDataByInsertDate(now);
		return list;
	}

	@Override
	public List<DataRecord> readDataSeven(String time7) {
		List<DataRecord> list7 = new ArrayList<DataRecord>();
		list7 = dataRecordDAO.readDataByTimeSeven(time7);
		return list7;
	}

	@Override
	public List<DataRecord> readDataThirty(String time30) {
		List<DataRecord> list30 = new ArrayList<DataRecord>();
		list30 = dataRecordDAO.readDataByTimeThirty(time30);
		return list30;
	}
	
}
