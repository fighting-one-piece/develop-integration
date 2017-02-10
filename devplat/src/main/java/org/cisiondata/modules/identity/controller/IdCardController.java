package org.cisiondata.modules.identity.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datada.service.IDatadaService;
import org.cisiondata.modules.identity.service.IIdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/card")
public class IdCardController {
	
	@Autowired
	private IIdCardService idCardService = null;
	@Autowired
	private IDatadaService datadaService = null;
	@ResponseBody
	@RequestMapping(value="/search")
	public WebResult readTypesDatas(@RequestParam String idCard){
		WebResult result = new WebResult();
		//定义判别身份证号的正则表达式
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//通过Pattern获得Matcher
		Matcher idNumMatcher = idNumPattern.matcher(idCard);
		try {
			//判断是否为身份证号
			if(idNumMatcher.matches()){
				List<Map<String, Object>> list = idCardService.readCardDatas(idCard);
				List<Map<String, Object>> listmap = datadaService.readDatadaDatas(idCard);
				if(listmap.size() > 0){
					for (int j = 0; j < listmap.size(); j++) {
						list.add(listmap.get(j));
					}
				}
				for (int i = 0; i < list.size(); i++) {
					list.get(i).remove("更新时间");
					list.get(i).remove("录入时间");
					list.get(i).remove("源文件");
					list.get(i).remove("index");
				}
				result.setData(list);
				result.setCode(ResultCode.SUCCESS.getCode());
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/user/user_idCard");
	}
}
