package org.cisiondata.modules.elasticsearch.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ESController {
	
	private Logger LOG = LoggerFactory.getLogger(ESController.class);
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	
	@ResponseBody
	@RequestMapping(value = "/search")
	public WebResult readIndexsTypesDatas(String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}", query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = esBizService.readPaginationDataListByCondition(query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/search/multi/scroll")
	@ResponseBody
	public WebResult readIndexsTypesDatasByFields(@RequestParam Map<String,String> map){
		WebResult result = new WebResult();
		try {
			result.setData(esBizService.readPaginationDataListByIndexType(map));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/search/multi")
	public ModelAndView readIndexsTypesDatasByFieldsView (){
		return new ModelAndView("es/multi");
	}
	
	@ResponseBody
	@RequestMapping(value = "/logistics/search")
	public WebResult readFinancialLogisticsDatas(String query) {
		LOG.info("query:{}", query);
		WebResult result = new WebResult();
		try {
			Object data = esBizService.readLogisticsDataList(query);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ext/logistics/search")
	public WebResult extReadFinancialLogisticsDatas(String query) {
		LOG.info("query:{}", query);
		WebResult result = new WebResult();
		try {
			Object data = esBizService.readLogisticsDataList(query);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/logistics/search/view")
	public ModelAndView amapView (){
		return new ModelAndView("user/amap");
	}
	
	@ResponseBody
	@RequestMapping(value = "/app/labels/search")
	public WebResult readLabelsAndHits(String query, String token) {
		LOG.info("query:{} token:{}", query, token);
		WebResult result = new WebResult();
		try {
			Object data = esBizService.readLabelsAndHitsByCondition(query);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	} 
	
	@ResponseBody
	@RequestMapping(value = "/app/index/{index}/type/{type}/search")
	public WebResult readLabelPaginationDataList(@PathVariable String index, @PathVariable String type,
			String query, String scrollId, int rowNumPerPage, String token) {
		LOG.info("index:{} type:{} query:{} scrollId:{} rowNumPerPage:{} token:{}", index, type,
				query, scrollId, rowNumPerPage, token);
		WebResult result = new WebResult();
		try {
			Object data = esBizService.readLabelPaginationDataList(index, type, query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	} 
	
}
