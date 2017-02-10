package org.cisiondata.modules.elasticsearch.service.impl;

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
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.modules.elasticsearch.service.IESExportService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service("esEportService")
public class ESExportServiceImpl  implements IESExportService {

	@Resource(name = "esBizService")
	private IESBizService esBizService =null;
	
	@SuppressWarnings({ "resource", "deprecation" })
	@Override
	public void readDataListForExport(HttpServletRequest req, HttpServletResponse resp,String query) throws BusinessException,IOException  {
		List<Map<String,Object>> lists = esBizService.readDataListByCondition(query,100);
//		System.err.println(lists+"---list1");
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
				Map<String, Object> map = (Map<String, Object>) lists.get(i);
				List<String> listKey = new ArrayList<String>();
				List<String> listValue = new ArrayList<String>();
				for (Map.Entry<String, Object> key : map.entrySet()) {
					listKey.add(key.getKey());
					listValue.add(key.getValue().toString());
				}
//				System.out.println("Key"+listKey);
//				System.out.println("值"+listValue);
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
	
	@Override
	public List<Map<String,Object>> readPaginationDataListByExs(String query, String scrollId, int size) throws BusinessException {
		QueryResult<Map<String, Object>> qr = esBizService.readPaginationDataListByCondition(query, scrollId, size);
		List<Map<String, Object>> lists = qr.getResultList();
		return  lists;
	}
}
