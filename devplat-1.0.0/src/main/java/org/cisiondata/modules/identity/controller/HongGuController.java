package org.cisiondata.modules.identity.controller;


import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.identity.service.IHongGuService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HongGuController {
	
	private Logger LOG = LoggerFactory.getLogger(HongGuController.class);
	
	@Resource(name="hongGuService")
	private IHongGuService hongGuService;
	
	//朋友网url查找qq号，编号为1的操作
	@ResponseBody
	@RequestMapping(value="/qqs/pengyou",method=RequestMethod.GET)
	public WebResult pywtoqq(String url){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.pywToQQ(url));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
		
	//微博url查找qq号，编号为2的操作
	@ResponseBody
	@RequestMapping(value="/qqs/weibo",method=RequestMethod.GET)
	public WebResult wbtoqq(String url){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.wbToQQ(url));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
		
	// qq查找朋友网，编号为3的操作
	@ResponseBody
	@RequestMapping(value="/qqs/{qq}/pengyou",method=RequestMethod.GET)
	public WebResult qqtopyw(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqToPyw(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//qq反找微博，编号为4的操作
	@ResponseBody
	@RequestMapping(value="/qqs/{qq}/tweibo",method=RequestMethod.GET)
	public WebResult qqtowb(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqTowb(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//对查找qq发起临时会话，编号为5的操作
	@ResponseBody
	@RequestMapping(value="/qqs/{qq}/tsession",method=RequestMethod.GET)
	public WebResult qqtosession(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.temporaryWindow(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//qq查找最后说说，编号为6的操作
	@ResponseBody
	@RequestMapping(value="/qqs/{qq}/lasttalk",method=RequestMethod.GET)
	public WebResult qqtotalk(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqLastShuoShuo(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//手机号找qq，编号为7的操作
	@ResponseBody
	@RequestMapping(value="/qqs/phone/{phone}",method=RequestMethod.GET)
	public WebResult phonetoqq(String phone){
		WebResult result = new WebResult();
//		result.setCode(ResultCode.FAILURE.getCode());
//		result.setFailure("系统维护升级中");
		try {
			result.setData(hongGuService.phoneNumToQQ(phone));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu) {
			result.setCode((bu.getCode()));
			result.setFailure(bu.getMessage());
			LOG.error(bu.getMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	
}
