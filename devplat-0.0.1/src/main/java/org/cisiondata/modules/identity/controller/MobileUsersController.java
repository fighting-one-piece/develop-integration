package org.cisiondata.modules.identity.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.elasticsearch.controller.ESController;
import org.cisiondata.modules.identity.service.IMobileUsersService;
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
 * 通过手机号码查询出同电话号码信息
 * 
 * @author Administrator
 *
 */
@Controller
public class MobileUsersController {
	
	private Logger LOG = LoggerFactory.getLogger(ESController.class);
	
	@Autowired
	private IMobileUsersService  mobileUsersService =null;
	
	/**
	 * 105 108电话号码查询
	 * @param mobile
	 * @return
	 */	
	@ResponseBody
	@RequestMapping(value = "phone/{phone}/users", method = RequestMethod.GET)
	public WebResult readphoneNUmber(@PathVariable String phone) {
		WebResult result = new WebResult();
		try {
			result.setData(mobileUsersService.readphoneNUmbers(phone));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value = "/phone/users", method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/user/user_phoneuser");
	}
}
