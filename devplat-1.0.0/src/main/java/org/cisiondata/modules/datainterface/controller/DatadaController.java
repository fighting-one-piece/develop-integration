package org.cisiondata.modules.datainterface.controller;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.IDatadaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/datada")
public class DatadaController {
	
	private Logger LOG = LoggerFactory.getLogger(DatadaController.class);
	
	@Autowired
	private IDatadaService datadaService = null;
	
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public WebResult readDatadaDatas(String query, String dateline, String searchToken) {
		LOG.info("query : " + query + " dateline: " + dateline + " searchToken:" + searchToken);
		WebResult result = new WebResult();
		try {
			Object data = datadaService.readDatadaDatas(query, dateline, searchToken);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//查询手机号码是否在集群中
	@ResponseBody
	@RequestMapping(value="/{phone}/exist")
	public WebResult readDataPhone(@PathVariable String phone) {
		WebResult result = new WebResult();
		try {
			result.setData(datadaService.readPhoneIsExists(phone));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//查询身份证号码是否存在集群中
		@ResponseBody
		@RequestMapping(value="/{idcard}/exist")
		public WebResult readDataIdCard(@PathVariable String idCard) {
			WebResult result = new WebResult();
			try {
				result.setData(datadaService.readIdCardIsExists(idCard));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	
}
