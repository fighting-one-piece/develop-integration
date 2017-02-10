package org.cisiondata.modules.user.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.entity.RoleUser;
import org.cisiondata.modules.user.service.IAGroupService;
import org.cisiondata.modules.user.service.IAUserService;
import org.cisiondata.modules.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AUserController {
	
	@Resource(name="aUserService")
	private IAUserService aUserService;
	@Autowired
	private IAGroupService groupService;
	@Resource(name="reloe")
	private IRoleService iroleService;
	//增加用户
	@RequestMapping(value="/admin/addauser",method=RequestMethod.POST)
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
	@RequestMapping("/admin/deleteauser")
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
	@RequestMapping("/admin/updateauser")
	@ResponseBody
	public WebResult updateAUser(AUser auser){
		WebResult result = new WebResult();
		System.out.println(auser.getIdentity());
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
	@RequestMapping("/admin/selectauser")
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
	
	//显示所有用户及分页查询
	@RequestMapping("/admin/page")
	@ResponseBody
	public WebResult selectAllAUser(int index){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(aUserService.selectAllAUser(index));
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//查询group中元素
	@RequestMapping("/admin/selectgroup")
	@ResponseBody
	public WebResult selectgroup(Long userid) {
		WebResult result = new WebResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("group", groupService.selAll());
			map.put("usergroup", aUserService.findusergroup(userid));
			result.setData(map);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
		}
		return result;
	}
	
	//插入group中元素
		@RequestMapping("/admin/insertgroup")
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
		@RequestMapping("/admin/selectrole")
		@ResponseBody
		public WebResult selectrole(Long userid) {
			System.out.println("==========userid:"+userid);
			WebResult result = new WebResult();
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				List<RoleUser> roleuser = new ArrayList<RoleUser>();
				List<AUserARole> userrole = new ArrayList<AUserARole>();
				userrole = aUserService.findusertrole(userid);
				roleuser = iroleService.readDataRole();
				System.out.println("userrole++++++:"+userrole);
				System.out.println("roleuser======:"+roleuser);
				map.put("role", roleuser);
				map.put("userrole", userrole);
				result.setData(map);
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setData(e.getMessage());
			}
			return result;
		}
		
		//插入role中元素
			@RequestMapping("/admin/insertrole")
			@ResponseBody
			public WebResult insertrole(String roleid) {
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
			
			
	@RequestMapping(value="/admin/auser",method=RequestMethod.GET)
	public String auserInterface() {
		return "admin/auser";
	}
}
