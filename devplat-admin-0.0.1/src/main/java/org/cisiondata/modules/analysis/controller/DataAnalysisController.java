package org.cisiondata.modules.analysis.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.analysis.entity.EventBase;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.cisiondata.modules.analysis.service.IEventBaseSerivce;
import org.cisiondata.utils.excel.PoiExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

@Controller
@RequestMapping("/data_analy")
public class DataAnalysisController {
	@Autowired
	private IEventBaseSerivce baseSerivce;
	private static Workbook wb;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
	// 导入数据
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST)
	public WebResult ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebResult result = new WebResult();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
		MultipartFile file = mRequest.getFile("upfile");
		EventBase event = new EventBase();
		// 文件名及类型
		String fileName = file.getOriginalFilename();
		// 获取文件名称
		String filename = fileName.substring(0, fileName.lastIndexOf("."));
		// 判断是否存在该名称
		int num = baseSerivce.selEvent(filename);
		Map<String, Object> maprs = new HashMap<String, Object>();
		List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
		if (num <= 0) {
			wb = PoiExcelUtils.ReadExcelUtils(file, fileName);
			if (wb == null) {
				throw new Exception("Workbook对象为空！");
			}
			// 循环每一页，并处理当前循环页
			sheet = wb.getSheetAt(0);
			// 获取表头信息
			List<String> listBiao = new ArrayList<String>();
			for (int i = 0; i <= sheet.getLastRowNum();) {
				for (int m = sheet.getRow(i).getFirstCellNum(); m <= sheet.getRow(i).getLastCellNum(); m++) {
					cell = sheet.getRow(0).getCell(m);
					if (cell == null) {
						continue;
					}
					listBiao.add(PoiExcelUtils.getStringVal(cell));
				}
				break;
			}
			String headerCode = PoiExcelUtils.listToString(listBiao);
			if(headerCode.indexOf("电话号码") != -1 || headerCode.indexOf("身份证号码") != -1){
				maprs.put("listBiao", listBiao);
				int max = sheet.getRow(0).getLastCellNum() - 1;
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					Map<Object, Object> rowMap = new HashMap<Object, Object>();
					// 获取每一行
					row = sheet.getRow(j);
					if(row!=null){
						int min = row.getFirstCellNum();
						// 遍历行，获取cell元素
						for (int k = min; k <= max; k++) {
							cell = row.getCell(k);
							if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								rowMap.put(listBiao.get(k), "");
								continue;
							}
							rowMap.put(listBiao.get(k), PoiExcelUtils.getStringVal(cell));
						}
						listMap.add(rowMap);
					}
				}
				event.setEventName(filename);
				event.setEventTime(new Date());
				event.setEventInfo(PoiExcelUtils.listToString(listBiao));
				baseSerivce.addEvent(event);
				// 获取当前增加的主键ID
				int id = event.getEventId();
				Gson gson = new Gson();
				for (int n = 1; n < listMap.size(); n++) {
					EventExtend extend = new EventExtend();
					String strJson = gson.toJson(listMap.get(n));
					extend.setExtendInfo1(strJson);
					extend.setEventBaseid(id);
					baseSerivce.addExtend(extend);
				}
				maprs.put("resultCode", "success");
			}else{
				maprs.put("resultCode", "error");
			}
			response.setCharacterEncoding("utf-8"); // 防止ajax接受到的中文信息乱码
		}
		maprs.put("listMap", listMap);
		result.setData(maprs);
		result.setCode(num);
		return result;
	}

	// 导出数据
	@RequestMapping(value = "/exportexcel")
	public void ajaxExportExcel(HttpServletRequest request, HttpServletResponse response,String fileName) throws IOException {
		OutputStream os = null;  
		try {
//			XHSSFWorkbook wb = baseSerivce.selExtend("锦衣卫科技100个");
			XSSFWorkbook wb = baseSerivce.selExtend(fileName);
			String ExpfileName = "导出Excel.xlsx";  
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(ExpfileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
			wb.close();
		} catch (Exception e) {
			
		}
	}
	//下载模板数据
	@RequestMapping(value="/exportDemoExcel",method={RequestMethod.GET,RequestMethod.POST})
	public void ExportDemoExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OutputStream os = null;  
		Workbook wb = null;    //工作薄
		try {
			PoiExcelUtils util = new PoiExcelUtils();
			File file =util.getExcelDemoFile("/ExcelDemoFile/模板下载.xls");
			wb = util.writeExcel(file);
			String fileName="模板下载.xls";
			response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
		    wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			os.flush();
			os.close();
			wb.close();
		}
	}
	//查询所导入的文件名
	@ResponseBody
	@RequestMapping(value= "/seleventBase")
	public WebResult selEventBase(int index){
		WebResult result = new WebResult();
		Map<String, Object> map = new HashMap<String,Object>();
		List<EventBase> list = baseSerivce.findAll();
		//计算总页数
		if(list != null && list.size() > 0){
			 pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页显示的数据
		page = (index - 1) * 10;
		try {
			List<EventBase> listPage = baseSerivce.selEventBase(page, count);
			map.put("list", listPage);
			map.put("pageCount", pageCount);
			result.setData(map);
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
			Map<String, Object> map = baseSerivce.selExtendPage(name, index);
			result.setData(map);
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
			int code = baseSerivce.delExtend(name);
			if(code >= 0){
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(baseSerivce.delEventBase(name));
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	// 数据分析页面
	@RequestMapping(method = RequestMethod.GET)
	public String toMoblie() {
		return "user/dataAnalysis";
	}
}
