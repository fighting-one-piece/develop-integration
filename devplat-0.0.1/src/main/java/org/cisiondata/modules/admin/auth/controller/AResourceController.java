package org.cisiondata.modules.admin.auth.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.admin.auth.entity.AResource;
import org.cisiondata.modules.admin.auth.service.IAResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/aResource")
public class AResourceController {
	private Logger LOG = LoggerFactory.getLogger(AResourceController.class);

	@Resource(name = "aResourceService")
	private IAResourceService aResourceService;

	@ResponseBody
	@RequestMapping("/all")
	public WebResult getAll() {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findAll());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/allAndParentName")
	public WebResult getAllAndParentName() {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findAll());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/add")
	public WebResult addAResource(AResource aResource) {
		System.out.println("add"+aResource.toString());
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.addLink(aResource));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/menuLink")
	public WebResult findMenuLink() {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findMenuLink());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@RequestMapping("/toAdd")
	public ModelAndView readAddAResourceView() {
		return new ModelAndView("admin/link");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public WebResult updateAResourceToDelete(Long id) {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.deleteById(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/find/id")
	public WebResult findAResourceById(Long id) {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findById(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public WebResult updateAResource(AResource aResource) {
		System.out.println(aResource.toString());
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.updateById(aResource));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/find/type")
	public WebResult findAResourceByType(int type) {
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findByType(type));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/find/page")
	public WebResult findResourceByPage(int page,int pageSize){
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findByPage(page, pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/adminMenu")
	public WebResult findAdminMenu(AResource aResource){
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findAdminMenu(aResource));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/tree")
	public WebResult findResourceTree(){
		WebResult result = new WebResult();
		try {
			result.setData(aResourceService.findResourceTree());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
}
