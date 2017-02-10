package org.cisiondata.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class PoiExcelUtils {
	private static Workbook wb;
	private final static String excel2003L =".xls";    //2003- 版本的excel
	private final static String excel2007U =".xlsx";   //2007+ 版本的excel
	/**
	 * 描述:根据文件路径获取项目中的文件
	 * @param fileDir
	 * @return
	 * @throws Exception
	 */
	public File getExcelDemoFile(String fileDir) throws Exception{
		String classDir = null;
		String fileBaseDir = null;
		File file = null;
		classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
		file = new File(fileBaseDir+fileDir);
		if(!file.exists()){
			throw new Exception("模板文件不存在！");
		}
		return file;
	}
	@SuppressWarnings("resource")
	public Workbook writeExcel(File file) throws Exception{
		Workbook wb = null;
		FileInputStream fis = new FileInputStream(file);
		String fileType = file.getName().substring(file.getName().lastIndexOf("."));
		if(excel2003L.equals(fileType)){
			wb = new HSSFWorkbook(fis);
		}else if(excel2007U.equals(fileType)){
			wb = new XSSFWorkbook(fis);
		}else{
			throw new Exception("解析的文件格式有误");
		}
		return wb;
	}
	// 将List<String>集合转换成字符串以逗号隔开
	public static String listToString(List<String> set) {
		if (set == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : set) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	//将字符串以逗号分隔后存入List
	public static List<String> stringToList(String str){
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
	// 判断文件格式
	public static Workbook ReadExcelUtils(MultipartFile filepath, String file) {
		if (filepath == null) {
		}
		String ext = file.substring(file.lastIndexOf("."));
		try {
			InputStream is = filepath.getInputStream();
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

	// 格式化数据
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
}
