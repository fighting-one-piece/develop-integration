package org.cisiondata.modules.identity.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.elasticsearch.controller.ESController;
import org.cisiondata.modules.identity.service.IMobileIdCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 分类查询接受请求
 * @author Administrator
 *
 */
@Controller
public class MobileIdCardController {
	//日志
	private Logger LOG = LoggerFactory.getLogger(ESController.class);
	
	@Autowired
	private IMobileIdCardService mobileIdCardService=null;
	
	
	
	/**
	 * 用于接收标签接收查询
	 * @param indexs
	 * @param types
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value= "/identity/{identity}/index/{index}/type/{type}",method=RequestMethod.GET)
	public WebResult readClassifiedQuery(@PathVariable String index,@PathVariable String type,@PathVariable String identity) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileIdCardService.redClassifiedQuery(index,type,identity));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/identity/{identity}/labels", method = RequestMethod.GET)
	public WebResult aredClassifiedQuery(@PathVariable String identity) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileIdCardService.readStatisticsAndLabels(identity));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	@RequestMapping(value ="/identity/labels", method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/user/user_phoneCard");
	}
	
}
