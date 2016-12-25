package org.cisiondata.modules.identity.controller;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.modules.identity.service.IQQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/qqdata")
public class QQController {
	
	@Autowired
	private IQQService qqService = null;
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	//根据QQ号码查询对应的群信息
	@ResponseBody
	@RequestMapping(value="/search")
	public WebResult readQQDatas(@RequestParam String qq){
		WebResult result = new WebResult();
		try {
			result.setData(qqService.readQQDatas(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/qqnick")
	//通过QQ昵称得到群信息
	public WebResult readQQNickDatas(String qqnick,String scrollId, int rowNumPerPage){
		WebResult result = new WebResult();
		try {
			qqnick = URLDecoder.decode(qqnick , "utf-8");
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(qqService.readQQNickData(qqnick,scrollId,rowNumPerPage));
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/qqsearch")
	public WebResult readQQData(@RequestParam String qq){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = qqService.readQQData(qq);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("源文件");
				list.get(i).remove("录入时间");
				list.get(i).remove("index");
				list.get(i).remove("type");
			}
			result.setData(list);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/qun")
	//得到群信息
	public WebResult readQQqunDatas(@RequestParam String qunNum){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = qqService.readQQqunDatas(qunNum);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("源文件");
				list.get(i).remove("录入时间");
				list.get(i).remove("index");
				list.get(i).remove("type");
			}
			result.setData(list);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//QQ查询页面
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/qq/qqQuery");
	}
}
