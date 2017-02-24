package org.cisiondata.modules.identity.controller;


import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.identity.service.IHongGuService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/honggu")
public class HongGuController {
	
	@Resource(name="hongGuService")
	private IHongGuService hongGuService;
	
	//朋友网url查找qq号，编号为1的操作
	@RequestMapping("/pywtoqq")
	@ResponseBody
	public WebResult pywtoqq(String url){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.pywToQQ(url));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
		
	//微博url查找qq号，编号为2的操作
	@RequestMapping("/wbtoqq")
	@ResponseBody
	public WebResult wbtoqq(String url){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.wbToQQ(url));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
		
	// qq查找朋友网，编号为3的操作
	@RequestMapping("/qqtopyw")
	@ResponseBody
	public WebResult qqtopyw(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqToPyw(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
	
	//qq反找微博，编号为4的操作
	@RequestMapping("/qqtowb")
	@ResponseBody
	public WebResult qqtowb(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqTowb(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
	
	//对查找qq发起临时会话，编号为5的操作
	@RequestMapping("/qqtosession")
	@ResponseBody
	public WebResult qqtosession(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.temporaryWindow(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
	
	//qq查找最后说说，编号为6的操作
	@RequestMapping("/qqtotalk")
	@ResponseBody
	public WebResult qqtotalk(String qq){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.qqLastShuoShuo(qq));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
	
	//手机号找qq，编号为7的操作
	@RequestMapping("/phonetoqq")
	@ResponseBody
	public WebResult phonetoqq(String phone){
		WebResult result = new WebResult();
		try {
			result.setData(hongGuService.phoneNumToQQ(phone));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure("系统错误");
		}
		return result;
	}
	
	
}
