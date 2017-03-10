package org.cisiondata.modules.log.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.log.entity.UserAccessLog;
import org.cisiondata.modules.log.service.IUserAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/log")
public class LogController {
	
	@Autowired
	private IUserAccessLogService userAccessLogService;
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
			boolean flas = userAccessLogService.delLog(keyword);
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
			result.setData(userAccessLogService.countPage(index));
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
			result.setData(userAccessLogService.keyByPage(index,keyword));
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
			List<UserAccessLog> list = userAccessLogService.findAll();
			//计算总页数
			if(list != null && list.size() > 0){
				pageCount =  list.size() % 10 == 0 ? list.size() / 10 : list.size() / 10 + 1;
			}
			//计算当前页显示的数据
			page = (index - 1) * 10;
			List<UserAccessLog> listPage = userAccessLogService.selectByorderTime(page, count);
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
}
