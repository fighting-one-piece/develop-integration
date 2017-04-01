package org.cisiondata.modules.search.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.search.service.IAdvancedService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdvancedController {
	private Logger LOG = LoggerFactory.getLogger(AdvancedController.class);

	@Resource(name = "advancedService")
	private IAdvancedService advancedService;
	
	@RequestMapping(value="/advanced/indices/financial/types/logistics/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readLogisticsDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial", "logistics", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/financial/types/finance/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readFinanceDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial", "finance", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/financial/types/business/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readBusinessDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial", "business", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/financial/types/car/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readCarDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial", "car", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/financial/types/house/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readHouseDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial", "house", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/account/types/account/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAccountDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "account", "account", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/account/types/accountjd/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAccountjdDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "account", "accountjd", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/account/types/accountht/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAccounthtDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "account", "accountht", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/email/types/email/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readEmailDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "email", "email", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/financial_new/types/business/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readBusinessNewDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "financial_new", "business", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/operator/types/telecom/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readTelecomDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "operator", "telecom", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/other/types/bocaioriginal/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readBocaioriginalDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "other", "bocaioriginal", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/other/types/contact/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readContactDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "other", "contact", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/other/types/internet/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readInternetDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "other", "internet", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/qq/types/qqqunrelation/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readQQqunrelationDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "qq", "qqqunrelation", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/qq/types/qqdata/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readQQdataDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "qq", "qqdata", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/qq/types/qqqundata/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readQQqundataDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "qq", "qqqundata", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/trip/types/hotel/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readHotelDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "trip", "hotel", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/trip/types/airplane/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAirplaneDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "trip", "airplane", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/student/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readStudentDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "student", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/accumulationfund/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readAccumulationfundDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "accumulationfund", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/healthproducts/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readHealthproductsDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "healthproducts", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/motherandbady/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readMotherandbadyDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "motherandbady", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/socialsecurity/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readSocialsecurityDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "socialsecurity", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/qualification/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readQualificationDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "qualification", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/elder/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readElderDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "elder", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/resume/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readResumeDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "resume", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	@RequestMapping(value="/advanced/indices/work/types/hospital/search",method = RequestMethod.GET)
	@ResponseBody
	public WebResult readHospitalDatasByMultiFields(HttpServletRequest req,String scrollId,Integer rowNumPerPage,String query){
		WebResult result = new WebResult();
		try {
			result.setData(advancedService.readPaginationDataListByMultiCondition(req, "work", "hospital", query ,scrollId, rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (BusinessException bu) {
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
}
