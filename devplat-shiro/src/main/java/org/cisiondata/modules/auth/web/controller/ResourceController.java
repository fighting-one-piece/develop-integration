package org.cisiondata.modules.auth.web.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.auth.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	private Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	@Resource(name="resourceService")
	private IResourceService resourceServie = null;
	
	
	@RequestMapping("/menuResource")
	@ResponseBody
	public WebResult getMenuResource() {
		WebResult result = new WebResult();
		try {
			result.setData(resourceServie.readResourceByType());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
