package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IAResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resourcedf")
public class ResourceDfController {
	@Resource(name="aResourceService")
	private IAResourceService resourceService = null;
	
	//查询角色已授权资源
	@ResponseBody
	@RequestMapping(value="/query",method = RequestMethod.GET)
	public WebResult resourceByUr(Long userId){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(resourceService.selResourceByUr(userId));
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		}catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//保存角色资源
	@ResponseBody
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public WebResult addResourceByUr(Long userId, String resourceId){
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(resourceService.addResourceByUr(userId, resourceId));
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		}catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	// 分页查询--用户资源关系
	@ResponseBody
	@RequestMapping(value="/queryusersource",method = RequestMethod.GET)
	public WebResult selByUR(int index, int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(resourceService.selByUR(index, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setData(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	//更新删除标识
	@ResponseBody
	@RequestMapping(value = "/updateFlag", method = RequestMethod.POST)
	public WebResult updateRoleByFlag(int userId,int resourceId,Boolean deleteFlag) {
		WebResult result = new WebResult();
		try {
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(resourceService.updateURByFlag(userId,resourceId,deleteFlag));
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	// 分页查询--根据账号查询用户定制资源的关系
	@ResponseBody
	@RequestMapping(value="/queryUrByName",method = RequestMethod.GET)
	public WebResult selUrByName(String name ,int index, int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(resourceService.selUrByName(name,index, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setData(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setData(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
}
