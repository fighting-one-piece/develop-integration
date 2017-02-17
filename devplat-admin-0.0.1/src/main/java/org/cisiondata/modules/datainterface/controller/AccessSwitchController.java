package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.entity.AccessSwitch;
import org.cisiondata.modules.datainterface.service.IAccessSwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessSwitchController {
	private Logger LOG = LoggerFactory.getLogger(AccessSwitchController.class);

	@Resource(name ="aaccessSwitchService")
	private IAccessSwitchService aaccessSwitchService;
	
	//查询所有
	@ResponseBody
	@RequestMapping("/switch/findAll")
	public WebResult findAll(){
		WebResult result = new WebResult();
		try {
			result.setData(aaccessSwitchService.findAll());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	//添加数据
	@ResponseBody
	@RequestMapping("/switch/saveSwitch")
	public WebResult insertSwitch(String switch_identity,String switch_name, String swith_desc,Integer status){
		WebResult result = new WebResult();
		try {
			AccessSwitch sSwitch =new AccessSwitch();
			sSwitch.setSwitch_identity(switch_identity);
			sSwitch.setSwitch_name(switch_name);
			sSwitch.setSwith_desc(swith_desc);
			sSwitch.setStatus(status);
			aaccessSwitchService.saveSwitch(sSwitch);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	//根据ID删除
	@ResponseBody
	@RequestMapping("/switch/deleteSwitch")
	public WebResult deleteSwitch(String identity){
		WebResult result = new WebResult();
		try {
			aaccessSwitchService.deleteIdentity(identity);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	//根据标识修改
	@ResponseBody
	@RequestMapping("/switch/updateSwitch")
	public WebResult updateSwitch(int id,String identity,String name, String desc,Integer status){
		WebResult result = new WebResult();
		try {
			AccessSwitch sSwitch =new AccessSwitch();
			sSwitch.setId(id);
			sSwitch.setSwitch_identity(identity);
			sSwitch.setSwitch_name(name);
			sSwitch.setSwith_desc(desc);
			sSwitch.setStatus(status);
			aaccessSwitchService.updateId(sSwitch);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	//批量修改
	@ResponseBody
	@RequestMapping("/switch/updateIdStatus")
	public WebResult updateIdStatus(int id,Integer status){
		WebResult result = new WebResult();
		try {
			AccessSwitch sSwitch =new AccessSwitch();
			sSwitch.setId(id);
			sSwitch.setStatus(status);
			aaccessSwitchService.updateIdStatus(sSwitch);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	//接口开关
	@RequestMapping(value="/accessSwitch",method=RequestMethod.GET)
	public ModelAndView toMoblie() {
		return new ModelAndView("admin/switch");
	}
}
