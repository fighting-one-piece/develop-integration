package org.cisiondata.modules.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.entity.AGroup;
import org.cisiondata.modules.user.service.IAGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description：部门管理
 */
@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
	@Autowired
	private IAGroupService groupService;
	@ResponseBody
	@RequestMapping(value = "/all")
	public WebResult getAll(){
		WebResult result = new WebResult();
		try {
			result.setData(groupService.selAll());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/addGroup")
	public WebResult addGroup(AGroup group){
		String name = group.getName();
		WebResult result = new WebResult();
		group.setCreateTime(new Date());
		int code = groupService.selGroup(name);
		if(code <= 0){
			try {
				result.setData(groupService.addGroup(group));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/getIdGroup")
	public WebResult getIdSel(Long id){
		WebResult result = new WebResult();
		try {
			result.setData(groupService.getIdGroup(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/updateGroup")
	public WebResult update(AGroup group){
		WebResult result = new WebResult();
		try {
			result.setData(groupService.updateGroup(group));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/delGroup")
	public WebResult delGroup(Long id){
		WebResult result = new WebResult();
		try {
			//获取删除与用户相关联信息
			int userCode = groupService.delGUser(id);
			//获取删除与角色相关联信息
			int roleCode = groupService.delGRole(id);
			if(userCode >=0 && roleCode >= 0){
				int groupCode = groupService.delGroup(id);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(groupCode);
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/getByIdUser")
	public WebResult getByIdUser(Long id){
		WebResult result = new WebResult();
		Map<String, Object> map = new  HashMap<String, Object>();
		try {
			List<AGroup> listUser = groupService.getByIdUser(id);
			List<AGroup> listNot = groupService.getByIdNotUser(id);
			map.put("user", listUser);
			map.put("notUser", listNot);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(map);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/addGUser")
	public WebResult addGUser(String user_id,Long group_id){
		WebResult result = new WebResult();
		try {
			groupService.addGUser(user_id, group_id);
			result.setData("success");
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@RequestMapping(method = RequestMethod.GET)
    public String manager() {
        return "admin/organization";
    }

  
}