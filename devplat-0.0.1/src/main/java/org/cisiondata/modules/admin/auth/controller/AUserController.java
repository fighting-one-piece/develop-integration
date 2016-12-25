package org.cisiondata.modules.admin.auth.controller;


import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.admin.auth.entity.AUser;
import org.cisiondata.modules.admin.auth.service.IAGroupService;
import org.cisiondata.modules.admin.auth.service.IAUserService;
import org.cisiondata.modules.admin.auth.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin")
public class AUserController {
	
	@Resource(name="aUserService")
	private IAUserService aUserService;
	@Autowired
	private IAGroupService groupService;
	@Resource(name="reloe")
	private IRoleService iroleService;
	//增加用户
	@RequestMapping(value="/addauser",method=RequestMethod.POST)
	@ResponseBody
	public WebResult addAUser(AUser auser){
		WebResult result = new WebResult();
		try {
			aUserService.addAUser(auser);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("添加成功！");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//删除用户
	@RequestMapping("/deleteauser")
	@ResponseBody
	public WebResult deleteAUser(long id){
		WebResult result = new WebResult();
		try {
			aUserService.deleteAUser(id);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("删除成功");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//修改用户
	@RequestMapping("/updateauser")
	@ResponseBody
	public WebResult updateAUser(AUser auser){
		WebResult result = new WebResult();
		try {
			aUserService.updateAUser(auser);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("更新成功！");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//查询用户
	@RequestMapping("/selectauser")
	@ResponseBody
	public WebResult selectAUser(String account){
		WebResult result = new WebResult();
		try {
			if (aUserService.selectAUser(account) != null) {
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(aUserService.selectAUser(account));
			}else {
				result.setCode(ResultCode.FAILURE.getCode());
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//显示所有用户
	@RequestMapping("/page")
	@ResponseBody
	public WebResult selectAllAUser(){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(aUserService.selectAllAUser());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//查询group中元素
	@RequestMapping("/selectgroup")
	@ResponseBody
	public WebResult selectgroup() {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(groupService.selAll());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//插入group中元素
		@RequestMapping("/insertgroup")
		@ResponseBody
		public WebResult insertgroup(String groupid) {
			WebResult result = new WebResult();
			try {
				aUserService.insertgroup(groupid);
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setData(e.getMessage());
			}
			return result;
		}
		
		//查询role中元素
		@RequestMapping("/selectrole")
		@ResponseBody
		public WebResult selectrole() {
			WebResult result = new WebResult();
			try {
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(iroleService.readDataRole());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setData(e.getMessage());
			}
			return result;
		}
		
		//插入role中元素
			@RequestMapping("/insertrole")
			@ResponseBody
			public WebResult insertrole(String roleid) {
				System.out.println(roleid);
				WebResult result = new WebResult();
				try {
					aUserService.insertrole(roleid);
					result.setCode(ResultCode.SUCCESS.getCode());
				} catch (Exception e) {
					result.setCode(ResultCode.FAILURE.getCode());
					result.setData(e.getMessage());
				}
				return result;
			}
			
	@RequestMapping(value="/auser",method=RequestMethod.GET)
	public String auserInterface() {
		return "admin/auser";
	}
}
