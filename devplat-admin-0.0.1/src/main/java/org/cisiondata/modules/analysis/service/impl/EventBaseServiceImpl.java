package org.cisiondata.modules.analysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class EventBaseServiceImpl extends GenericServiceImpl<EventBase, Long> implements IEventBaseSerivce{

	@Resource(name = "eventBaseDao")
	private EventBaseDAO eventBaseDAO = null;
	
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
		return wb;
	}
	//查询所有文件名
	public List<EventBase> selEventBase(int startPos,int pageSize) {
		return eventBaseDAO.selEventBase(startPos,pageSize);
	}
	//删除文件名
	public int delEventBase(String name) {
		return eventBaseDAO.delEventBase(name);
	}
	//删除文件名的分析数据
	public int delExtend(String name) {
		return eventBaseDAO.delExtend(name);
	}
	//查询所有
	public List<EventBase> findAll() {
		return eventBaseDAO.findAll();
	}
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
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
	
}
