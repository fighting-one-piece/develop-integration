package org.cisiondata.AnalysisTest;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class AnaTest {

	@Resource(name = "esService")
	private IESService esService = null;

	// @Before
	// public void before() {
	// IESService esService = SpringBeanFactory.getBean("esService");
	// System.out.println(esService.readIdentityAttributes());
	// }
	//

	@SuppressWarnings("deprecation")
	public XSSFWorkbook selExtend(List<List<String>> list){
		XSSFWorkbook wb1 = new XSSFWorkbook();
		// 创建单元格样式  
		XSSFCellStyle cellStyleTitle = wb1.createCellStyle(); 
        // 指定单元格居中对齐  
        cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 指定单元格垂直居中对齐  
        cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 指定当单元格内容显示不下时自动换行  
        cellStyleTitle.setWrapText(false);  
        // 设置单元格字体  
        XSSFFont font = wb1.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyleTitle.setFont(font); 
        //创建工作薄
        XSSFSheet sheet1 = wb1.createSheet();
        //初始化行数
        int rownum1 = 0;
        //创建行与列
        XSSFRow row1;
        XSSFCell cell1;
        for (int k = 0,len = list.size(); k < len; k++) {
			List<String> listValue = list.get(k);
			//在当前行的后面继续写入值
			row1 = sheet1.createRow(rownum1);
			for (int m = 0; m < listValue.size(); m++) {
				cell1 = row1.createCell(m);
				cell1.setCellValue(listValue.get(m));
			}
			//获取下一行
			rownum1 += 1;
		}
		return wb1;
	}
	
	private static Workbook wb;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;

	public static Workbook ReadExcelUtils(String path) {
		if (path == null) {

		}
		String ext = path.substring(path.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(path);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return wb;
	}

	private static List<String> readxls(String path) throws Exception {
		wb = ReadExcelUtils(path);
		if (wb == null) {
			throw new Exception("不能为空！");
		}
		sheet = wb.getSheetAt(0);
//		List<List<String>> lists = new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			// int min = row.getFirstCellNum();
			// for (int j = min; j <= row.getLastCellNum(); j++) {
			cell = row.getCell(1);
			// if(cell == null){
			// continue;
			// }
			if(cell == null || null == getStringVal(cell) || "null".equals(getStringVal(cell)) || StringUtils.isBlank(getStringVal(cell))){
				break;
			}
			list.add(getStringVal(cell));
			// }
//			List<String> list = new ArrayList<String>();
//			row = sheet.getRow(i);
//			// int min = row.getFirstCellNum();
//			// for (int j = min; j <= row.getLastCellNum(); j++) {
//			cell = row.getCell(0);
//			// if(cell == null){
//			// continue;
//			// }
			if(cell == null || null == getStringVal(cell) || "null".equals(getStringVal(cell)) || StringUtils.isBlank(getStringVal(cell))){
				break;
			}
//			list.add(getStringVal(cell));
			// }
//			cell = row.getCell(1);
//			list.add(getStringVal(cell));
//			cell = row.getCell(2);
//			list.add(getStringVal(cell));
//			lists.add(list);
		}
//		for (int i = 0, len = list.size(); i < len; i++) {
//			System.out.println(list.get(i));
//		}
		return list;
	}

	@SuppressWarnings("deprecation")
	public static String getStringVal(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "true" : "false";
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
			return null;
		}
	}

	
	@Test
	public void testW() throws Exception {
		List<String> list = readxls("C:\\Users\\admin\\Desktop\\肖家河\\肖家河\\肖家河街路名.xlsx");
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("C:/Users/admin/Desktop/肖家河/肖家河/肖家河街路名.csv"), "GBK"));
		StringBuffer keysb = new StringBuffer();
		StringBuffer valuesb = new StringBuffer();

		for (int i = 0, len = list.size(); i < len; i++) {
			//物流收件人
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("address", list.get(i)));
			BoolQueryBuilder cityQueryBuilder = QueryBuilders.boolQuery();
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("address", "成都"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("address", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", "成都"));
			boolQueryBuilder.must(cityQueryBuilder);
			List<Map<String, Object>> resultList1 = esService.readDataListByCondition("financial", "logistics",
					boolQueryBuilder, false);
			System.out.println(list.get(i));
			System.err.println("匹配到的正确数据" + resultList1);
			
			//物流寄件人
			boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("linkAddress", list.get(i)));
			cityQueryBuilder = QueryBuilders.boolQuery();
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("linkAddress", "成都"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("linkAddress", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("linkCity", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("linkCity", "成都"));
			boolQueryBuilder.must(cityQueryBuilder);
			List<Map<String, Object>> resultList2 = esService.readDataListByCondition("financial", "logistics",
					boolQueryBuilder, false);
			System.err.println("匹配到的正确数据" + resultList2);
			
			//保健品
			boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("address", list.get(i)));
			cityQueryBuilder = QueryBuilders.boolQuery();
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("address", "成都"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("address", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", "成都市"));
			cityQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", "成都"));
			boolQueryBuilder.must(cityQueryBuilder);
			List<Map<String, Object>> resultList3 = esService.readDataListByCondition("work", "healthproducts",
					boolQueryBuilder, false);
			System.err.println("匹配到的正确数据" + resultList3);
			
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < resultList1.size(); j++) {
				valuesb.delete(0, valuesb.length());
				valuesb.append(",");
				Map<String, Object> map = resultList1.get(j);
				if (map.containsKey("收件人姓名")) {
					if("******".equals((String)map.get("收件人姓名"))) continue;
					valuesb.append(((String) map.get("收件人姓名")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("收件人手机号")) {
					valuesb.append(((String) map.get("收件人手机号")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("收件人地址")) {
					valuesb.append(((String) map.get("收件人地址")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				set.add(valuesb.toString());

			}
			for (int j = 0; j < resultList2.size(); j++) {
				valuesb.delete(0, valuesb.length());
				valuesb.append(",");
				Map<String, Object> map = resultList2.get(j);
				if (map.containsKey("寄件人姓名")) {
					if("******".equals((String)map.get("寄件人姓名"))) continue;
					valuesb.append(((String) map.get("寄件人姓名")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("寄件人手机号")) {
					valuesb.append(((String) map.get("寄件人手机号")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("寄件人地址")) {
					valuesb.append(((String) map.get("寄件人地址")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				set.add(valuesb.toString());
			}
			for (int j = 0; j < resultList3.size(); j++) {
				valuesb.delete(0, valuesb.length());
				valuesb.append(",");
				Map<String, Object> map = resultList3.get(j);
				if (map.containsKey("收件人姓名")) {
					if("******".equals((String)map.get("收件人姓名"))) continue;
					valuesb.append(((String) map.get("收件人姓名")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("收件人手机号1")) {
					valuesb.append(((String) map.get("收件人手机号1")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				if (map.containsKey("收件人地址")) {
					valuesb.append(((String) map.get("收件人地址")).replaceAll(",", "、"));
					valuesb.append(",");
				} else {
					valuesb.append(",");
				}
				set.add(valuesb.toString());
			}

			keysb.append(list.get(i));
			keysb.append(",姓名,电话,地址");
			bw.write(keysb.toString());
			System.out.println(keysb.toString());
			keysb.delete(0, keysb.length());
			bw.newLine();
			for (String value : set) {
				bw.write(value);
				System.out.println(value);
				bw.newLine();
			}

		}
		bw.close();
	}

//	@Test
//	public void testIdcard() throws Exception{
//		List<String> list = readxls("C:\\Users\\admin\\Desktop\\xyksl清单.xlsx");
//		BufferedWriter bw = new BufferedWriter(
//				new OutputStreamWriter(new FileOutputStream("C:/Users/admin/Desktop/xyksl清单.txt"), "GBK"));
//		StringBuffer keysb = new StringBuffer();
//		StringBuffer valuesb = new StringBuffer();
//		
//		Set<String> identityAttributes=esService.readIdentityAttributes();
//		for(String idCard : list){
//			System.out.println("idCard: " + idCard);
//			BoolQueryBuilder idCardQueryBuilder = QueryBuilders.boolQuery();
//			for (String identityAttribute:identityAttributes) {
//				idCardQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, idCard));    
//			}
//			List <Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
//			try{
//				resultList = esService.readDataListByCondition(idCardQueryBuilder,false);
//			} catch(Exception e){
//				e.printStackTrace();
//				System.out.println("查询出错");
//			}
//			
//			for(int i = 0; i < resultList.size(); i++){
//				keysb.delete(0, keysb.length());
//				valuesb.delete(0, valuesb.length());
//				if(i==0){
//					keysb.append(idCard);
//					keysb.append(",");
//				} else {
//					keysb.append(",");
//				}
//				valuesb.append(",");
//				Map<String,Object> map = resultList.get(i);
//				for(Entry<String,Object> entry : map.entrySet()){
//					keysb.append(entry.getKey());
//					keysb.append(",");
//					valuesb.append(((String)entry.getValue()).replaceAll(",", "、"));
//					valuesb.append(",");
//				}
//				bw.write(keysb.toString());
//				System.out.println(keysb.toString());
//				bw.newLine();
//				bw.write(valuesb.toString());
//				System.out.println(valuesb.toString());
//				bw.newLine();
//				
//			}
//			if(resultList.size() == 0){
//				keysb.delete(0, keysb.length());
//				keysb.append(idCard);
//				bw.write(keysb.toString());
//				bw.newLine();
//			}
//		}
//		bw.close();
//	}
	
	
//	@Test
//	public void testXinWangBank() throws Exception{
//		List<List<String>> lists = readxls("C:\\Users\\admin\\Desktop\\数之星测试样本.xlsx");
//		Set<String> identityAttributes=esService.readIdentityAttributes();
//		List<List<String>> resultList = new ArrayList<List<String>>();
//		for (int i = 0; i < lists.size(); i++) {
//			List<String> list = lists.get(i);
//			System.err.println("第"+i+"条");
//			String name = list.get(0);
//			String idCard = list.get(1);
//			String phone = list.get(2);
//			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//			for (String identityAttribute:identityAttributes) {
//                boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, idCard));
//                boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, phone));
//            }
//			List<Map<String,Object>> resultListMap = esService.readDataListByCondition(boolQueryBuilder,false);
//			System.err.println(resultListMap.size());
//			Map<String,Set<String>> keyMap = new HashMap<String,Set<String>>();
//			if(resultListMap.size() == 0){
//				List<String> lis = new ArrayList<String>();
//				lis.add(name);
//				lis.add(idCard);
//				lis.add(phone);
//				resultList.add(lis);
//			}
//			for (Map<String,Object> map : resultListMap) {
//				Set<String> keySet = new HashSet<String>();
//				String type = (String)map.get("type");
//				map.remove("index");
//				map.remove("插入时间");
//				map.remove("录入时间");
//				map.remove("源文件");
//				for (Entry<String,Object> entry : map.entrySet()) {
//					keySet.add(entry.getKey());
//				}
//				if(keyMap.containsKey(type)){
//					Set<String> s = keyMap.get(type);
//					s.addAll(keySet);
//					keyMap.put(type, s);
//				} else {
//					keyMap.put(type, keySet);
//					
//				}
//			}
//			Map<String,List<String>> keyMapList = new HashMap<String,List<String>>();
//			for(Entry<String,Set<String>> entry : keyMap.entrySet()){
//				List<String> l = new ArrayList<String>();
//				for (String str : entry.getValue()){
//					if (str == "type") continue;
//					l.add(str);
//				}
//				keyMapList.put(entry.getKey(), l);
//			}
//			int status = 0;
//			for (Entry<String,List<String>> entry : keyMapList.entrySet()) {
//					
//				List<String> reskeylist = new ArrayList<String>();
//				if(status == 0){
//					reskeylist.add(name);
//					reskeylist.add(idCard);
//					reskeylist.add(phone);
//					status++;
//				} else {
//					reskeylist.add("");
//					reskeylist.add("");
//					reskeylist.add("");
//				}
//				for(String s : entry.getValue()) {
//					reskeylist.add(s);
//				}
//				resultList.add(reskeylist);
//				for(Map<String,Object> m : resultListMap){
//					List<String> resList = new ArrayList<String>();
//					if (entry.getKey().equals((String)(m.get("type")))){
//						resList.add("");
//						resList.add("");
//						resList.add("");
//						for (String keystr : entry.getValue()){
//							if(m.containsKey(keystr)){
//								resList.add(((String)m.get(keystr)).replaceAll(",", "、").replaceAll("，", "、"));
//							} else {
//								resList.add("");
//							}
//						}
//						resultList.add(resList);
//					}
//				}
//			}
//		}
//		
//		FileOutputStream fileStream = new FileOutputStream("C:/Users/admin/Desktop/测试111.xlsx");
//		XSSFWorkbook wbs = this.selExtend(resultList);
//		wbs.write(fileStream);
//		wbs.close();
//		fileStream.close();
//		
//	}
	
}