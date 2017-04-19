package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apiresource")
public class APIResourceController {

	@Resource(name="aresourceService")
	private IResourceService resourceService;
	
	
	// 添加资源
		@ResponseBody
		@RequestMapping(value="/setting/add",method=RequestMethod.POST)
		public WebResult addResourceAPI(String name,String identity,String url) {
			WebResult result = new WebResult();
			try {
				Integer type = 5;
				resourceService.addResource(name, type, identity, url);
				result.setResultCode(ResultCode.SUCCESS);
				result.setData("增加成功");
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setFailure(bu.getMessage());
			}catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
	
	// 修改资源
		@ResponseBody
		@RequestMapping(value="/setting/edit",method=RequestMethod.POST)
		public WebResult editResourceAPI(Long id,String name,String identity,String url) {
			WebResult result = new WebResult();
			try {
				Integer type = 5;
				resourceService.editResource(id, name, type, identity, url);				
				result.setData("修改完成");
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setFailure(bu.getMessage());
			}catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
	// 查询所有资源
		@ResponseBody
		@RequestMapping(value="/qurey/resource",method=RequestMethod.GET)
		public WebResult qureyAllResourceAPI(Integer page, Integer pageSize) {
			WebResult result = new WebResult();
			try {
				Integer type = 5;
				result.setData(resourceService.qureyAllResource(page, pageSize, type));
				result.setResultCode(ResultCode.SUCCESS);
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setFailure(bu.getMessage());
			}catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
	// 通过ID启用、关闭资源
		@ResponseBody
		@RequestMapping(value="/setting/delete",method=RequestMethod.POST)
		public WebResult deleteResourceAPI(Long id,Boolean deleteFlag) {
			WebResult result = new WebResult();
			try {
				Integer type = 5;
				resourceService.deleteResource(id, deleteFlag, type);
				result.setResultCode(ResultCode.SUCCESS);
				result.setData("修改成功");
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setFailure(bu.getMessage());
			}catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
}
