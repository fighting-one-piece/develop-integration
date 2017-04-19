package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.service.IResourceAttributeService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * API机构资源展示Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/apiresourcepubshow")
public class APIResourceShowController {

private Logger LOG = LoggerFactory.getLogger(APIResourceShowController.class);
	
	@Resource(name="iResourceAttributeService")
	private IResourceAttributeService iResourceAttributeService=null;
	
	//根据resourceid查询
	@RequestMapping(value="/apifindbyidcondition",method = RequestMethod.GET)
	@ResponseBody
	public WebResult findByIdCondition(Long resourceid){
		WebResult result=new WebResult();
		try {
			result.setData(iResourceAttributeService.findByIdCondition(resourceid));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//根据resourceid修改
	@RequestMapping(value="/apiupdatebyidresourceattribute",method=RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public WebResult updateResourceAttributeById(Long resourceid,String fields){
		WebResult result=new WebResult();
		try {
			iResourceAttributeService.updateResourceAttributeByresourceId(resourceid,fields);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("保存成功");
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
