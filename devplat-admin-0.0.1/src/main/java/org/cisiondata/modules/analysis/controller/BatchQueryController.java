package org.cisiondata.modules.analysis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.analysis.service.IBatchQueryService;
import org.cisiondata.modules.elasticsearch.service.IESMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class BatchQueryController {
	private Logger LOG = LoggerFactory.getLogger(BatchQueryController.class);
	
	@Resource(name="batchQueryService")
	private IBatchQueryService batchQueryService;
	
	@Resource(name="esMetadataService")
	private IESMetadataService ieSMetadaService;
	
	//显示全部的index
	@ResponseBody
	@RequestMapping(value="/batchquery/typeall")
	public WebResult indexsAll(){
		WebResult result = new WebResult();
			try {
				result.setData(ieSMetadaService.readIndices());
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
		return result;
	}
	//获得对应的type 类型
	@ResponseBody
	@RequestMapping(value="/batchquery/type/types")
	public WebResult typesAll(String indexs){
		WebResult result=new WebResult();
		try {
			result.setData(ieSMetadaService.readIndexTypes(indexs)); 
			result.setCode(ResultCode.SUCCESS.getCode());			
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//分类型操作
	@ResponseBody
	@RequestMapping(value="/batchquery/{Classification}/{index}/{type}")
	public WebResult Classification(@PathVariable String Classification,@PathVariable String index,@PathVariable String type,HttpServletRequest request){
		MultipartHttpServletRequest mRquest=(MultipartHttpServletRequest)(request);
		MultipartFile file=mRquest.getFile("upfile");
		String fileName=file.getOriginalFilename();
		String filename = fileName.substring(0, fileName.lastIndexOf("."));
		System.out.println(filename);
		WebResult result=new WebResult();
		try {
			result.setData(batchQueryService.Classifi(Classification, index, type,filename));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;	
	}
	
	
	@ResponseBody
	@RequestMapping(value="/batchquery/{type}")
	public WebResult readClassifiedQuery(@PathVariable String type,HttpServletRequest request) {
		MultipartHttpServletRequest mRquest=(MultipartHttpServletRequest)(request);
		MultipartFile file=mRquest.getFile("upfile");
		
		String fileName=file.getOriginalFilename();
		String filename = fileName.substring(0, fileName.lastIndexOf("."));
		System.out.println(filename);
		WebResult result = new WebResult();
		try {
			result.setData(batchQueryService.updateStatistics(type,filename));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
}
