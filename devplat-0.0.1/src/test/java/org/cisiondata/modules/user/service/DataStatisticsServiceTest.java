package org.cisiondata.modules.user.service;

import javax.annotation.Resource;

import org.cisiondata.modules.admin.statistics.service.IDataRecordService;
import org.cisiondata.modules.elasticsearch.service.IESMetadataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class DataStatisticsServiceTest {

	@Resource(name = "dataRecordStatisticsService")
	private IDataRecordService dataRecordStatisticsService = null;
	
	@Before
	public void before() {
	}
	
	@Test
	public void testInsert() {
		//后一天记录前一天导入数据 为1
		//周一 记录周六导入数据 为2
		//周一 记录周五导入数据 为3
		//calendar.add(Calendar.DAY_OF_MONTH, -1);  DataStatisticsServiceImpl中
		dataRecordStatisticsService.insertDocNumAndDataNum(1);
	}
	
	@Resource(name = "esMetadataService")
	private IESMetadataService esMetadataService=null;
	
	@Test
	public void test(){
		Object data =  esMetadataService.readMetadatas();
		System.err.println(data);
	}
	
}
