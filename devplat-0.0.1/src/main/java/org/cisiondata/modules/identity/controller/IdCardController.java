package org.cisiondata.modules.identity.controller;

import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
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
	
	@ResponseBody
	@RequestMapping(value="/search")
	public WebResult readTypesDatas(@RequestParam String idCard){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = idCardService.readCardDatas(idCard);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("录入时间");
				list.get(i).remove("源文件");
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
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/user/user_idCard");
	}
}
