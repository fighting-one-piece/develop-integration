package org.cisiondata.modules.analysis.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.analysis.entity.DataRecord;
import org.cisiondata.modules.analysis.service.IDataRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/excludeUtils")
public class DataRecordController {

	@Resource(name = "dataRecordService")
	private IDataRecordService dataRecordService;
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public ModelAndView insertDataRecordView() {
		return new ModelAndView("/user/user_data");
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public WebResult insertDataRecord(String name, int dataNum, int docNum) {
		WebResult result = new WebResult();
		try {
			result.setData(dataRecordService.insertAndReadList(name, dataNum, docNum));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/shows", method = RequestMethod.GET)
	@ResponseBody
	public WebResult showData() {
		WebResult result = new WebResult();
		Date nowTime = new Date();   //当前时间
//		Date dBefore = new Date();
		Date dBefore7 = new Date();
		Date dBefore30 = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		Calendar calendar7 = Calendar.getInstance();
		Calendar calendar30 = Calendar.getInstance();
		calendar.setTime(nowTime);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		calendar7.add(Calendar.DAY_OF_MONTH, -7);
		calendar30.add(Calendar.DAY_OF_MONTH, -31);
//		dBefore = calendar.getTime();   //得到前一天的时间
		dBefore7 = calendar7.getTime();
		dBefore30 = calendar30.getTime();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
//		String time = sdf.format(dBefore);    //格式化前一天
		String time7 = sdf.format(dBefore7);
		String time30 = sdf.format(dBefore30);
		String now = sdf.format(nowTime); //格式化当前时间
		try {
			List<DataRecord> lists = new ArrayList<DataRecord>();
			
			lists = dataRecordService.readData(now);
			lists.addAll(dataRecordService.readDataSeven(time7));
			lists.addAll(dataRecordService.readDataThirty(time30));
			result.setData(lists);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView dataShow() {
		return new ModelAndView("/user/datashow");
	}
}
