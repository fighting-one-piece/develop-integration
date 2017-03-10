package org.cisiondata.AnalysisTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AnaTest {
	
	@Resource(name = "esService")
	private IESService esService = null;	
	
	private static Workbook wb;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	public static Workbook ReadExcelUtils(String path){
		if(path == null){
			
		}
		String ext = path.substring(path.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(path);
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(is);
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(is);
			}else{
				wb = null;
			}
		}catch(FileNotFoundException e){
		} catch (IOException e) {
		}
		return wb;
	}
	private static List<String> readxls(String path) throws Exception{
		wb = ReadExcelUtils(path);
		if(wb == null){
			throw new Exception("不能为空！");
		}
		sheet = wb.getSheetAt(0);
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
//			int min = row.getFirstCellNum();
//			for (int j = min; j <= row.getLastCellNum(); j++) {
				cell = row.getCell(1);
//				if(cell == null){
//					continue;
//				}
				list.add(getStringVal(cell));
//				}
		}
//		for (int i = 0,len = list.size(); i < len; i++) {
//			System.out.println(list.get(i));
//		}
		return list;
	}
	@SuppressWarnings("deprecation")
	public static String getStringVal(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue()? "true":"false";
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
	public void testW() throws Exception{
		List<String> list = readxls("E:\\交接\\肖家河\\肖家河街路名.xlsx");
		Gson gson = new Gson();
		for (int i = 0,len = list.size(); i < len; i++) {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			Set<String> identityAttributes = esService.readIdentityAttributes();
			for (String identityAttribute:identityAttributes) {
                boolQueryBuilder.should(QueryBuilders.termQuery(identityAttribute, list.get(i)));
            }
			List<Map<String,Object>> resultList = esService.readDataListByCondition(boolQueryBuilder,false);
            
            System.out.println("匹配到的正确数据"+resultList);
            //移除元素
            for (Map<String, Object>  rmes : resultList) {
                rmes.remove("index");
                rmes.remove("type");
                rmes.remove("插入时间");
                rmes.remove("录入时间");
                rmes.remove("源文件");
            }
          //转换写入
           String resultListJson = gson.toJson(resultList);
           System.out.println(resultListJson);
		}
	}
	
}