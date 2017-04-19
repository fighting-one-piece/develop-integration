package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IResourceAttributeService;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jytresource")
public class JYTResourceController {

	@Resource(name="aresourceService")
	private IResourceService resourceService;

	@Resource(name = "iResourceAttributeService")
	private IResourceAttributeService iResourceAttributeService = null;

		// 添加资源
		@ResponseBody
		@RequestMapping(value="/setting/add",method=RequestMethod.POST)
		public WebResult addResourceJYT(String name,String identity,String url) {
			WebResult result = new WebResult();
			try {
				Integer type = 6;
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
		public WebResult editResourceJYT(Long id,String name,String identity,String url) {
			WebResult result = new WebResult();
			try {
				Integer type = 6;
				resourceService.editResource(id, name, type, identity, url);
				result.setResultCode(ResultCode.SUCCESS);
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
		public WebResult qureyAllResourceJYT(Integer page, Integer pageSize) {
			WebResult result = new WebResult();
			try {
				Integer type = 6;
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
		public WebResult deleteResourceJYT(Long id,Boolean deleteFlag) {
			WebResult result = new WebResult();
			try {
				Integer type = 6;
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
		// 查看费用
		@ResponseBody
		@RequestMapping(value = "/query", method = RequestMethod.GET)
		public WebResult findResourceAttribute(Long resourceId) {
			WebResult result = new WebResult();
			try {
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(iResourceAttributeService
						.findByIdResourceAttribute(resourceId));
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setData(bu.getDefaultMessage());
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;

		}

		// 保存费用
		@ResponseBody
		@RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
		public WebResult updateResourceAttribute(Long resourceid, String chargings) {
			WebResult result = new WebResult();
			try {
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(iResourceAttributeService.updateResourceAttribute(
						resourceid, chargings));
			} catch (BusinessException bu) {
				result.setCode(bu.getCode());
				result.setData(bu.getDefaultMessage());
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
}
