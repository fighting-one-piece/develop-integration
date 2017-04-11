package org.cisiondata.modules.system.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.system.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Resource(name="aresourceService")
	private IResourceService resourceService;
	
	// 添加资源
	@ResponseBody
	@RequestMapping(value="/setting/add",method=RequestMethod.POST)
	public WebResult addResource(String name,Integer type,String identity,String url,String icon,Integer priority,Long parentId,Boolean deleteFlag) {
		WebResult result = new WebResult();
		try {
			resourceService.addResource(name, type, identity, url, icon, priority, parentId, deleteFlag);
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	// 修改资源
	@ResponseBody
	@RequestMapping(value="/setting/edit",method=RequestMethod.POST)
	public WebResult editResource(Long id,String name,Integer type,String identity,String url,String icon,Integer priority,Long parentId,Boolean deleteFlag) {
		WebResult result = new WebResult();
		try {
			resourceService.editResource(id, name, type, identity, url, icon, priority, parentId, deleteFlag);
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	// 查询所有资源
	@ResponseBody
	@RequestMapping(value="/qurey/resource",method=RequestMethod.GET)
	public WebResult qureyAllResource(Integer page, Integer pageSize) {
		WebResult result = new WebResult();
		try {
			result.setData(resourceService.qureyAllResource(page, pageSize));
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	// 通过ID启用、关闭资源
	@ResponseBody
	@RequestMapping(value="/setting/delete",method=RequestMethod.POST)
	public WebResult deleteResource(Long id,Boolean deleteFlag) {
		WebResult result = new WebResult();
		try {
			resourceService.deleteResource(id, deleteFlag);
			result.setResultCode(ResultCode.SUCCESS);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getMessage());
		}catch (Exception e) {
			result.setResultCode(ResultCode.FAILURE);
			result.setFailure(e.getMessage());
		}
		return result;
	}
}
