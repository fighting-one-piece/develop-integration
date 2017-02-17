package org.cisiondata.modules.analysis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.analysis.service.IEventBaseSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/data_analy")
public class DataAnalysisController {
	@Autowired
	private IEventBaseSerivce baseSerivce;
	private Logger LOG = LoggerFactory.getLogger(DataAnalysisController.class);
	// 导入数据
	@ResponseBody
	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST)
	public WebResult ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebResult result = new WebResult();
		try {
			result.setData(baseSerivce.uploadExcel(request, response));
			result.setCode(0);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	// 导出数据
	@RequestMapping(value = "/exportexcel")
	public void ajaxExportExcel(HttpServletRequest request, HttpServletResponse response,String fileName) throws Exception {
		try {
			baseSerivce.exportExcel(request, response, fileName);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
	//下载模板数据
	@RequestMapping(value="/exportDemoExcel",method={RequestMethod.GET,RequestMethod.POST})
	public void ExportDemoExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			baseSerivce.exportDemoExcel(request, response);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
	//查询所导入的文件名
	@ResponseBody
	@RequestMapping(value= "/seleventBase")
	public WebResult selEventBase(int index){
		WebResult result = new WebResult();
		try {
			result.setData(baseSerivce.selEventBase(index));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//查询当前原数据以及分析数据进行分页
	@ResponseBody
	@RequestMapping(value="/selExendPage")
	public WebResult selExendPage(String name,int index){
		WebResult result = new WebResult();
		try {
			result.setData(baseSerivce.selExtendPage(name, index));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//删除文件名以及分析的数据
	@ResponseBody
	@RequestMapping(value="/delEvent")
	public WebResult delEvent(String name){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(baseSerivce.delEventBase(name));
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	// 数据分析页面
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie() {
		return new ModelAndView("user/dataAnalysis");
	}
}
