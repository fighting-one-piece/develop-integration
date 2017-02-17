package org.cisiondata.modules.analysis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.analysis.entity.EventBase;
import org.cisiondata.modules.analysis.entity.EventExtend;

public interface IEventBaseSerivce extends IGenericService<EventBase, Long> {
	// 增加
	public int addEvent(EventBase event);

	// 增加事件详情
	public void addExtend(EventExtend extend);

	// 查询是否存在该名称
	public int selEvent(String name);

	// 查询当前分析的数据
	public XSSFWorkbook selExtend(String filename);
	
	//查询当前分析的数据
	public List<EventExtend> selExtends(String filename);
	
	//查询当前分析的数据进行分页
	public Map<String, Object> selExtendPage(String name,int index);
	
	//导入Excel文件数据
	public Map<String,Object> uploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	//查询全部所有
	public List<EventBase> findAll();
	
	// 查询当前所分析后的文件名
	public Map<String, Object> selEventBase(int index);

	// 删除所选的文件名
	public int delEventBase(String name);

	// 删除所选的文件分析数据
	public int delExtend(String name);
	
	//下载模板数据
	public void exportDemoExcel(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	//导出数据
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,String fileName) throws Exception;
}
