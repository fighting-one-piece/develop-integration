package org.cisiondata.modules.admin.statistics.controller;

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
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.admin.statistics.entity.EventBase;
import org.cisiondata.modules.admin.statistics.entity.EventExtend;
import org.cisiondata.modules.admin.statistics.service.IEventBaseSerivce;
import org.cisiondata.utils.excel.PoiExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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

	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST)
	public WebResult ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebResult result = new WebResult();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
		MultipartFile file = mRequest.getFile("upfile");
		EventBase event = new EventBase();
		
		// 文件名及类型
		String fileName = file.getOriginalFilename();
		// 获取文件名称
		String filename = fileName.substring(0, fileName.lastIndexOf("."));
		//判断是否存在该名称
		int num = baseSerivce.selEvent(filename);
		Map<String,Object> maprs = new HashMap<String, Object>();
		List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
		if(num <= 0){
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
			maprs.put("listBiao", listBiao);
			int max = sheet.getRow(0).getLastCellNum() - 1;
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				Map<Object, Object> rowMap = new HashMap<Object, Object>();
				// 获取每一行
				row = sheet.getRow(j);
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
			response.setCharacterEncoding("utf-8"); // 防止ajax接受到的中文信息乱码
		}
		maprs.put("listMap", listMap);
		result.setData(maprs);
		result.setCode(num);
		return result;
	}

	// 数据分析页面
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie() {
		return new ModelAndView("/user/data_analysis");
	}
}
