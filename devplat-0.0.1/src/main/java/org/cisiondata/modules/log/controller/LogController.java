package org.cisiondata.modules.log.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
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

	//删除某条日志
	@RequestMapping(value="dellog")
	@ResponseBody
	public WebResult dellog(String keyword){
		WebResult result = new WebResult();
		try {
			boolean flas = logService.delLog(keyword);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(flas);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value ="allLog")
	@ResponseBody
	//统计关键字
	public WebResult getCount(int index){
		WebResult result = new WebResult();
		try {
			result.setData(logService.countPage(index));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "allkeyword")
	@ResponseBody
	//点击关键字
	public WebResult getKeyWord(int index,String keyword){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(logService.keyByPage(index,keyword));
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "ordertime")
	@ResponseBody
	//点击关键字
	public WebResult selectOrderTime(int index){
		WebResult result = new WebResult();
		try {
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
			result.setData(map);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/log/logQuery");
	}
	
}
