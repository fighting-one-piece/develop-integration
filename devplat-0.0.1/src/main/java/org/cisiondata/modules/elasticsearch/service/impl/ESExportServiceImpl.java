package org.cisiondata.modules.elasticsearch.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.shiro.SecurityUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.auth.entity.AccessUserControl;
import org.cisiondata.modules.auth.service.IAccessUserService;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.modules.elasticsearch.service.IESExportService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.message.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("esEportService")
public class ESExportServiceImpl  implements IESExportService {

	@Resource(name = "esBizService")
	private IESBizService esBizService =null;
	
	@Resource(name = "accessUserService")
	private IAccessUserService accessUserService;
	
	@SuppressWarnings({ "resource", "deprecation" })
	@Override
	@Transactional
	public void dataListForExport(HttpServletRequest req, HttpServletResponse resp,String query) throws BusinessException,IOException  {
		List<Map<String,Object>> lists = esBizService.readDataListByCondition(query,100,false);
		try{
			//扣费
			String account = (String) SecurityUtils.getSubject().getPrincipal();
			double incOrDec = lists.size() * 5D;
			if (StringUtils.isBlank(account)) throw new BusinessException("账号不能为空");
			AccessUserControl accessUserControl =  accessUserService.readAccessUserControlByAccount(account);
			if (accessUserControl.getRemainingMoney() < incOrDec) throw new BusinessException("账户余额不足");
			accessUserService.updateRemainingMoney(account, -incOrDec);
		} catch (BusinessException bu) {
			HSSFWorkbook wb = new HSSFWorkbook();
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			// 创建一个工作Sheet
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell = row1.createCell(0);
			cell.setCellValue(bu.getDefaultMessage());
			resp.setContentType("application/x-download");
			String filedisplay = "查询清单.xls";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			resp.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
	        OutputStream ouputStream = resp.getOutputStream();  
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();
		} catch (Exception e) {
			HSSFWorkbook wb = new HSSFWorkbook();
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			// 创建一个工作Sheet
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell = row1.createCell(0);
			cell.setCellValue("导出Excel错误");
			resp.setContentType("application/x-download");
			String filedisplay = "查询清单.xls";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			resp.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
	        OutputStream ouputStream = resp.getOutputStream();  
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();
		}
		
		try {
			
			HSSFWorkbook wb = new HSSFWorkbook();
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			// 创建一个工作Sheet
			HSSFSheet sheet = wb.createSheet();
			//设置背景颜色
			 HSSFCellStyle style = wb.createCellStyle();
			 HSSFCellStyle style1 = wb.createCellStyle();
			 HSSFDataFormat format = wb.createDataFormat();
			 style1.setDataFormat(format.getFormat("@"));
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
			List<String> type = new ArrayList<String>();
			//获取类型
			for (int i = 0; i < lists.size(); i++) {
				Map<String, Object> map = (Map<String, Object>)lists.get(i);
				if (!type.contains((String)map.get("type"))) type.add((String)map.get("type")); 
			}
			//获取每个类型的字段
			for (String ty : type) {
				Set<String> set = new HashSet<String>();
				List<String> keyList = new ArrayList<String>();
				List<Map<String,Object>> li = new ArrayList<Map<String,Object>>();
				for (Map<String,Object> map : lists) {
					if(ty.equals((String)map.get("type"))){
						//将这条数据放入list
						li.add(map);
					}
				}
				//遍历list获取字段名，放入set
				for (Map<String,Object> map : li) {
					for (Entry<String,Object> entry : map.entrySet()) {
						if("type".equals(entry.getKey()) || "index".equals(entry.getKey()) || "源文件".equals(entry.getKey()) || "sourceFile".equals(entry.getKey())) continue;
						set.add(entry.getKey());
					}
				}
				//将去重后的key放入list
				for (String s : set) {
					keyList.add(s);
				}
				//写入头文件
				row1 = sheet.createRow(irow1);
				for (int j = 0 ; j < keyList.size() ; j++ ) {
					cell = row1.createCell(j);
					cell.setCellStyle(style);
					cell.setCellValue(keyList.get(j));
				}
				cell = row1.createCell(keyList.size());
				cell.setCellStyle(style);
				cell.setCellValue("类型");
				irow1++;
				//遍历list
				for (Map<String,Object> map : li) {
					row1 = sheet.createRow(irow1);
					for (int j = 0 ; j < keyList.size() ; j++ ) {
						cell = row1.createCell(j);
						cell.setCellStyle(style1);
						if (map.containsKey(keyList.get(j))){
							String value = (String)map.get(keyList.get(j));
							if (StringUtils.isBlank(value) || "NA".equals(value)) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(value);
							}
						} else {
							cell.setCellValue("");
						}
					}
					cell = row1.createCell(keyList.size());
					cell.setCellStyle(style1);
					String code = ((String)map.get("index"))+"."+((String)map.get("type"));
					cell.setCellValue(MessageUtils.getInstance().getMessage(code));
					irow1++;
				}
				
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
