package org.cisiondata.modules.admin.auth.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.admin.auth.entity.RoleUser;
import org.cisiondata.modules.admin.auth.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController {
	
	@Resource(name="reloe")
	private IRoleService iroleService;
	
	
   //查询指定的某一条数据
	@ResponseBody
	@RequestMapping(value="/certains/{id}")
	public WebResult getCertain(@PathVariable Long id){
		WebResult result =new WebResult();
		try {
			result.setData(iroleService.readDataCertain(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;	
	}
	
	//修改
	@ResponseBody
	@RequestMapping(value="/updata")
	public WebResult getUpdata(RoleUser roleUser){
		WebResult result =new WebResult();
		try {
			result.setData(iroleService.readUpdata(roleUser));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
		
	}
	

	@ResponseBody
	@RequestMapping(value="/role")
	public WebResult getAll(){
		WebResult result =new WebResult();
		try {
			result.setData(iroleService.readDataRole());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/roles", method = RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/admin/role");
	}
}
