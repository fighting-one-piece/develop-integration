package org.cisiondata.modules.analysis.service.impl;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cisiondata.modules.abstr.dao.GenericDAO;
import org.cisiondata.modules.abstr.service.impl.GenericServiceImpl;
import org.cisiondata.modules.analysis.dao.EventBaseDAO;
import org.cisiondata.modules.analysis.entity.EventBase;
import org.cisiondata.modules.analysis.entity.EventExtend;
import org.cisiondata.modules.analysis.service.IEventBaseSerivce;
import org.cisiondata.utils.excel.PoiExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

@Service
public class EventBaseServiceImpl extends GenericServiceImpl<EventBase, Long> implements IEventBaseSerivce{

	@Resource(name = "eventBaseDao")
	private EventBaseDAO eventBaseDAO = null;
	
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
	
	private static Workbook wb;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	
	@Override
	public GenericDAO<EventBase, Long> obtainDAOInstance() {
		return eventBaseDAO;
	}
	
	@Override
	public int addEvent(EventBase event) {
		int reust = eventBaseDAO.addEvent(event);
		return reust;
	}
	
	@Override
	public void addExtend(EventExtend extend) {
		eventBaseDAO.addExtend(extend);
	}
	
	@Override
	public int selEvent(String name) {
		return eventBaseDAO.selEvent(name);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public XSSFWorkbook selExtend(String filename) {
		//获取数据
		List<EventExtend> list = eventBaseDAO.selExtend(filename);
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建单元格样式  
		XSSFCellStyle cellStyleTitle = wb.createCellStyle(); 
        // 指定单元格居中对齐  
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 指定单元格垂直居中对齐  
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 指定当单元格内容显示不下时自动换行  
        cellStyleTitle.setWrapText(false);  
        // 设置单元格字体  
        XSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyleTitle.setFont(font); 
        //创建工作薄
        XSSFSheet sheet = wb.createSheet();
        //初始化行数
        int rownum = 0;
        //创建行与列
        XSSFRow row;
        XSSFCell cell;
		//取出数据
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = gson.fromJson(list.get(i).getExtendInfo1(), Map.class);
			List<Map<String, Object>> datas = gson.fromJson((list.get(i)).getExtendInfo2(), List.class);
			if(datas != null){
				datas.add(0, map);
				if(datas.size() == 1){
					Map<String, Object> maps = datas.get(0);
					List<String> listkey = new ArrayList<String>();
					List<String> listvalue = new ArrayList<String>();
					for (Map.Entry<String, Object> entry: maps.entrySet()) {
						listkey.add(entry.getKey());
						listvalue.add(entry.getValue().toString());
					}
					//在当前行的后面继续写入值
					row = sheet.createRow(rownum);
					for (int k = 0; k < listkey.size(); k++) {
						//获取上面List的大小在后面继续写入值
						cell = row.createCell(k);
						cell.setCellValue(listkey.get(k));
						cell.setCellStyle(cellStyleTitle);
					}
					row = sheet.createRow(++rownum);
					for (int m = 0; m < listvalue.size(); m++) {
						cell = row.createCell(m);
						cell.setCellValue(listvalue.get(m));
					}
					//获取下一行
					rownum += 1;
				}else{
					for (int j = 0; j < datas.size(); j++) {
						if(j == 0){
							Map<String, Object> maps = datas.get(j);
							Map<String, Object> mapss = datas.get(j+1);
							List<String> listkey = new ArrayList<String>();
							List<String> listvalue = new ArrayList<String>();
							for (Map.Entry<String, Object> entry: maps.entrySet()) {
								listkey.add(entry.getKey());
								listvalue.add(entry.getValue().toString());
							}
							for (Map.Entry<String, Object> entry: mapss.entrySet()) {
								listkey.add(entry.getKey());
								listvalue.add(entry.getValue().toString());
							}
							//在当前行的后面继续写入值
							row = sheet.createRow(rownum);
							for (int k = 0; k < listkey.size(); k++) {
								//获取上面List的大小在后面继续写入值
								cell = row.createCell(k);
								cell.setCellValue(listkey.get(k));
								cell.setCellStyle(cellStyleTitle);
							}
							row = sheet.createRow(++rownum);
							for (int m = 0; m < listvalue.size(); m++) {
								cell = row.createCell(m);
								cell.setCellValue(listvalue.get(m));
							}
							//获取下一行
							rownum += 1;
						}
						if(j>1){
							Map<String, Object> maps = datas.get(j);
							List<String> listkey = new ArrayList<String>();
							List<String> listvalue = new ArrayList<String>();
							for (Map.Entry<String, Object> entry: maps.entrySet()) {
								listkey.add(entry.getKey());
								listvalue.add(entry.getValue().toString());
							}
							//在当前行的后面继续写入值
							row = sheet.createRow(rownum);
							for (int k = 0; k < listkey.size(); k++) {
								//获取上面List的大小在后面继续写入值
								cell = row.createCell(datas.get(0).size()+k);
								cell.setCellValue(listkey.get(k));
								cell.setCellStyle(cellStyleTitle);
							}
							row = sheet.createRow(++rownum);
							for (int m = 0; m < listvalue.size(); m++) {
								cell = row.createCell(datas.get(0).size()+m);
								cell.setCellValue(listvalue.get(m));
							}
							//获取下一行
							rownum += 1;
						}
					}
				}
			}
		}
		return wb;
	}
	//查询所有文件名
	public Map<String, Object> selEventBase(int index) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<EventBase> list = findAll();
		//计算总页数
		if(list != null && list.size() > 0){
			 pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页显示的数据
		page = (index - 1) * 10;
		List<EventBase> listPage = eventBaseDAO.selEventBase(page,count);
		map.put("list", listPage);
		map.put("pageCount", pageCount);
		return map;
	}
	//删除文件名
	public int delEventBase(String name) {
		int resultCode = 0;
		int code = delExtend(name);
		
		if(code >= 0){
			resultCode = eventBaseDAO.delEventBase(name);
		}
		return resultCode;
	}
	//删除文件名的分析数据
	public int delExtend(String name) {
		return eventBaseDAO.delExtend(name);
	}
	//查询所有
	public List<EventBase> findAll() {
		return eventBaseDAO.findAll();
	}
	
	//查询当前分析的数据进行分页
	@SuppressWarnings("unchecked")
	public Map<String, Object> selExtendPage(String name, int index) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		List<EventExtend> list = eventBaseDAO.selExtend(name);
		if(list != null && list.size() > 0){
			pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页显示的数据
		page = (index - 1) * 10;
		List<EventExtend> listpage = eventBaseDAO.selExtendPage(name, page, count);
		for (int i = 0; i < listpage.size(); i++) {
			Gson gson = new Gson();
			Map<String, Object> mapBase = gson.fromJson(listpage.get(i).getExtendInfo1(), Map.class);
			listMap.add(mapBase);
		}
		map.put("listBase", listMap);
		map.put("list", listpage);
		map.put("pageCount", pageCount);
		return map;
	}

	public List<EventExtend> selExtends(String filename) {
		return eventBaseDAO.selExtend(filename);
	}
	//导入Excel文件的数据
	@SuppressWarnings("deprecation")
	public Map<String, Object> uploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
		MultipartFile file = mRequest.getFile("upfile");
		EventBase event = new EventBase();
		// 文件名及类型
		String fileName = file.getOriginalFilename();
		// 获取文件名称
		String filename = fileName.substring(0, fileName.lastIndexOf("."));
		// 判断是否存在该名称
		int num = selEvent(filename);
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
			if(headerCode.indexOf("电话号码") != -1 || headerCode.indexOf("身份证号码") != -1 || headerCode.indexOf("邮箱地址") != -1 || headerCode.indexOf("公司信息") != -1){
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
				addEvent(event);
				// 获取当前增加的主键ID
				int id = event.getEventId();
				Gson gson = new Gson();
				for (int n = 1; n < listMap.size(); n++) {
					EventExtend extend = new EventExtend();
					String strJson = gson.toJson(listMap.get(n));
					extend.setExtendInfo1(strJson);
					extend.setEventBaseid(id);
					addExtend(extend);
				}
				maprs.put("resultCode", "success");
			}else{
				maprs.put("resultCode", "error");
			}
			response.setCharacterEncoding("utf-8"); // 防止ajax接受到的中文信息乱码
		}
		maprs.put("listMap", listMap);
		return maprs;
	}
	//下载模板数据
	public void exportDemoExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
	//导出数据
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String fileName)
			throws Exception {
		OutputStream os = null;  
		try {
//			XHSSFWorkbook wb = baseSerivce.selExtend("锦衣卫科技100个");
			XSSFWorkbook wb = this.selExtend(fileName);
			if(wb != null){
				String ExpfileName = "导出Excel.xlsx";  
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(ExpfileName, "utf-8"));
				os = response.getOutputStream();
				wb.write(os);
				os.flush();
				os.close();
				wb.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
