package org.cisiondata.modules.search.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.search.service.ILabelsService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LabelsController {
	private Logger LOG = LoggerFactory.getLogger(LabelsController.class);

	@Resource(name = "labelsService")
	private ILabelsService labelsService;
	
	/**
	 * 标签查询
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/labels",method = RequestMethod.GET)
	public WebResult readLabelsAndHits(String query) {
		LOG.info("query:{}", query);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readLabelsAndHitsAndClassifiedIncludeTypes(query);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu){
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
	
	
	@ResponseBody
	@RequestMapping(value = "/labels/indices/financial/types/logistics",method = RequestMethod.GET)
	public WebResult readLogisticsPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial", "logistics", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/financial/types/finance",method = RequestMethod.GET)
	public WebResult readFinancePaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial", "finance", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/financial/types/business",method = RequestMethod.GET)
	public WebResult readBusinessPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial", "business", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/financial/types/car",method = RequestMethod.GET)
	public WebResult readCarPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial", "car", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/financial/types/house",method = RequestMethod.GET)
	public WebResult readHousePaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial", "house", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/account/types/account",method = RequestMethod.GET)
	public WebResult readAccountPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "account", "account", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/account/types/accountjd",method = RequestMethod.GET)
	public WebResult readAccountjdPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "account", "accountjd", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/account/types/accountht",method = RequestMethod.GET)
	public WebResult readAccounthtPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "account", "accountht", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/email/types/email",method = RequestMethod.GET)
	public WebResult readEmailPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "email", "email", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/financial_new/types/business",method = RequestMethod.GET)
	public WebResult readBusinessNewPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "financial_new", "business", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/operator/types/telecom",method = RequestMethod.GET)
	public WebResult readTelecomPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "operator", "telecom", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/other/types/bocaioriginal",method = RequestMethod.GET)
	public WebResult readBocaioriginalPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "other", "bocaioriginal", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/other/types/contact",method = RequestMethod.GET)
	public WebResult readContactPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "other", "contact", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/other/types/internet",method = RequestMethod.GET)
	public WebResult readInternetPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "other", "internet", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/qq/types/qqqunrelation",method = RequestMethod.GET)
	public WebResult readQQqunrelationPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "qq", "qqqunrelation", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/qq/types/qqdata",method = RequestMethod.GET)
	public WebResult readQQdataPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "qq", "qqdata", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/qq/types/qqqundata",method = RequestMethod.GET)
	public WebResult readQQqundataPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "qq", "qqqundata", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/trip/types/hotel",method = RequestMethod.GET)
	public WebResult readHotelPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "trip", "hotel", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/trip/types/airplane",method = RequestMethod.GET)
	public WebResult readAirplanePaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "trip", "airplane", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/student",method = RequestMethod.GET)
	public WebResult readStudentPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "student", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/accumulationfund",method = RequestMethod.GET)
	public WebResult readAccumulationfundPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "accumulationfund", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/healthproducts",method = RequestMethod.GET)
	public WebResult readHealthproductsPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "healthproducts", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/motherandbady",method = RequestMethod.GET)
	public WebResult readMotherandbadyPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "motherandbady", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/socialsecurity",method = RequestMethod.GET)
	public WebResult readSocialsecurityPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "socialsecurity", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/qualification",method = RequestMethod.GET)
	public WebResult readQualificationPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "qualification", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/elder",method = RequestMethod.GET)
	public WebResult readElderPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "elder", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/resume",method = RequestMethod.GET)
	public WebResult readResumePaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "resume", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
	@RequestMapping(value = "/labels/indices/work/types/hospital",method = RequestMethod.GET)
	public WebResult readHospitalPaginationDataList(HttpServletRequest req,String query, String scrollId, int rowNumPerPage) {
		LOG.info("query:{} scrollId:{} rowNumPerPage:{}",query, scrollId, rowNumPerPage);
		WebResult result = new WebResult();
		try {
			Object data = labelsService.readPaginationDataListByCondition(req, "work", "hospital", query, scrollId, rowNumPerPage);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch(BusinessException bu) {
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
