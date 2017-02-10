package org.cisiondata.modules.elasticsearch.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.cisiondata.modules.elasticsearch.service.IESExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ESExportController {
	
	@Resource(name = "esEportService")
	private IESExportService esEportService = null;
	
	//导出
	@ResponseBody
	@RequestMapping(value = "/export/xls")
	public void readIndexsTypesDatasEXpoi(HttpServletRequest req, HttpServletResponse resp,String query) {
		System.out.println(query+"--query--");
		try {
			esEportService.readDataListForExport(req, resp, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//导出2
	@RequestMapping(value = "/search/exyy")
	@SuppressWarnings({ "resource", "unchecked", "deprecation"})
	public void exportExcel(HttpServletRequest req, HttpServletResponse resp,String query, String scrollId, int rowNumPerPage)throws IOException {
		List<Map<String, Object>> lists  = esEportService.readPaginationDataListByExs(query, scrollId, rowNumPerPage);
		try {
		HSSFWorkbook wb = new HSSFWorkbook();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// 创建一个工作Sheet
		HSSFSheet sheet = wb.createSheet();
		//设置背景颜色
		 HSSFCellStyle style = wb.createCellStyle();
		 style.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
		 style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		 style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		 style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		 style.setFillForegroundColor((short) 26);// 设置背景色
		 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 创建第一行
		int irow1 = 0;
		HSSFRow row1;
		HSSFCell cell;
		for (int i = 0; i < lists.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) lists.get(i).get("data");
			String index = (String) lists.get(i).get("index");
			List<String> listKey = new ArrayList<String>();
			List<String> listValue = new ArrayList<String>();
			for (Map.Entry<String, Object> key : map.entrySet()) {
				listKey.add("库名");
				listKey.add(key.getKey());
				listValue.add(index);
				listValue.add(key.getValue().toString());
			}
//			System.out.println("Key"+listKey);
//			System.out.println("值"+listValue);
			row1 = sheet.createRow(irow1+i);
			for (int j = 0; j < listKey.size(); j++) {
				cell = row1.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(listKey.get(j));
			}
			row1 = sheet.createRow(irow1+i+1);
			for (int k = 0; k < listValue.size(); k++) {
				cell = row1.createCell(k);
				cell.setCellValue(listValue.get(k));
			}
			irow1 = irow1+2;
		}
		resp.setContentType("application/x-download");
		String filedisplay = "查询清单.xls";
		filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
        OutputStream ouputStream = resp.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
