package org.cisiondata.modules.search.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.search.service.IESMetadataService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/metadatas")
public class ESMetadataController {
	
	private Logger LOG = LoggerFactory.getLogger(ESMetadataController.class);
	
	@Autowired
	private IESMetadataService esMetadataService = null;
	
	@ResponseBody
	@RequestMapping(value = "/indices/types/metadatas")
	public WebResult readIndicesTypesMetadatas() {
		WebResult result = new WebResult();
		try {
			Object data = esMetadataService.readMetadatas();
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/indices")
	public WebResult readIndices() {
		WebResult result = new WebResult();
		try {
			Object data = esMetadataService.readIndices();
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/indices/{index}/types")
	public WebResult readIndexTypes(@PathVariable String index) {
		WebResult result = new WebResult();
		try {
			Object data = esMetadataService.readIndexTypes(index);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/indices/types/all")
	public WebResult readIndiciesTypesMetadatas() {
		WebResult result = new WebResult();
		try {
			Object data = esMetadataService.readIndicesTypesCN();
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/indices/{index}/types/{type}/fields")
	public WebResult readIndexTypeAttributes(@PathVariable String index, @PathVariable String type) {
		WebResult result = new WebResult();
		try {
			Object data = esMetadataService.readIndexTypeAttributes(index, type);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/indices/types/metadatas/cleanup")
	public WebResult updateMetadatasNull() {
		WebResult result = new WebResult();
		try {
			esMetadataService.updateMetadatasNull();
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(null);
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(), bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
}
