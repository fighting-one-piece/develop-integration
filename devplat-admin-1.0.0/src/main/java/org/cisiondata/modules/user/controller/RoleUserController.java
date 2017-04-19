package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IAResourceService;
import org.cisiondata.modules.user.service.IRoleUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/orgrole")
public class RoleUserController {
	@Resource(name = "roleUserService")
	private IRoleUserService roleService = null;
	@Resource(name = "aResourceService")
	private IAResourceService resourceService = null;

	@ResponseBody
	@RequestMapping(value = "/addorgrole", method = RequestMethod.POST)
	public WebResult addRoleUser(String name, String identity, String desc) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(roleService.addRole(name, identity, desc));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/qureyallorgrole", method = RequestMethod.GET)
	public WebResult selRole(int index, int pageSize) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(roleService.selRoleByPage(index, pageSize));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	public WebResult updateRoleByFlag(int id,Boolean deleteFlag) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(roleService.updateRoleByFlag(id,deleteFlag));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public WebResult updateRole(String name, String identity, String desc, int id) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(roleService.updateRole(name, identity, desc, id));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/tree",method = RequestMethod.GET)
	public WebResult selResourceById(Long roleId){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(resourceService.selResourceById(roleId));
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/updatePermission",method = RequestMethod.POST)
	public WebResult addPermission(String resourceId, Long roleId){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(resourceService.addPermission(resourceId, roleId));
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/addUserRoles",method = RequestMethod.POST)
	public WebResult addUserRoles(Long role_id,String user_id ,String  priority){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(roleService.addUserRoles(role_id, user_id ,priority));
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
		
	}
	
}
