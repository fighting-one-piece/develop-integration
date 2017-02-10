package org.cisiondata.modules.log.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.log.entity.LogModel;
import org.cisiondata.modules.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/log")
public class LogController {
	@Autowired
	private ILogService logService;
	//分页
	int pageCount = 0; //总页数
	int count = 10;  //每页显示的条数
	int page = 0; //计算每页从哪里开始读取数据
	
	public Map<String, Object> getAll(int index){
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = logService.findAll();
		//计算总页数
		if(list != null && list.size() > 0){
			 pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页显示的数据
		page = (index - 1) * 10;
		List<LogModel> listPage = logService.selectByPage(page, count);
		map.put("list", listPage);
		map.put("pageCount", pageCount);
		return map;
	}
	
	//删除某条日志
	@RequestMapping(value="dellog")
	@ResponseBody
	public boolean dellog(String keyword){
		boolean flas = logService.delLog(keyword);
		return flas;
	}
	@RequestMapping(value ="allLog")
	@ResponseBody
	//统计关键字
	public Map<String, Object> getCount(int index){
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = logService.count();
		if(list != null && list.size() > 0){
			pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页数数据量
		page= (index -1) * 10;
		List<LogModel> listPage = logService.countPage(page, count);
		map.put("data", listPage);
		map.put("pageCount", pageCount);
		return map;
	}
	@RequestMapping(value = "allkeyword")
	@ResponseBody
	//点击关键字
	public Map<String, Object> getKeyWord(int index,String keyword){
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = logService.selectByKey(keyword);
		if(list != null && list.size() > 0){
			pageCount = list.size() % 10 == 0? list.size() / 10 : list.size() /10 + 1;
		}
		//计算当前页数数据量
		page = (index -1) * 10;
		List<LogModel> listPage = logService.keyByPage(keyword, page, count);
		map.put("data", listPage);
		map.put("pageCount", pageCount);
		return map;
	}
	
	@RequestMapping(value = "ordertime")
	@ResponseBody
	//点击关键字
	public Map<String, Object> selectOrderTime(int index){
		Map<String, Object> map = new HashMap<String,Object>();
		List<LogModel> list = logService.findAll();
		//计算总页数
		if(list != null && list.size() > 0){
			 pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
		}
		//计算当前页显示的数据
		page = (index - 1) * 10;
		List<LogModel> listPage = logService.selectByorderTime(page, count);
		map.put("data", listPage);
		map.put("pageCount", pageCount);
		return map;
	}
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/log/logQuery");
	}
	
}
