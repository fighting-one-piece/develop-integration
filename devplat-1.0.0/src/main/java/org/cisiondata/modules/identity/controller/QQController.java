package org.cisiondata.modules.identity.controller;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.es.service.IESService;
import org.cisiondata.modules.identity.service.IQQService;
import org.cisiondata.modules.search.service.IESBizService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/")
public class QQController {
	
	private Logger LOG = LoggerFactory.getLogger(QQController.class);
	
	@Autowired
	private IQQService qqService = null;
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
	
	@ResponseBody
	@RequestMapping(value = "/qqs/{qq}/base",method=RequestMethod.GET)
	//根据QQ号码查询基础信息和对应的群信息
	public WebResult readQQ(@RequestParam String qq,HttpServletRequest request){
		WebResult result = new WebResult();
		try {
			//得到基本信息
			Map<String, Object> listBase = qqService.readQQData(qq ,request);
			result.setData(listBase);
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		}  catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	
	//根据QQ号码查询对应的群信息
	@ResponseBody
	@RequestMapping(value="/qqs/{qq}/quns",method=RequestMethod.GET)
	public WebResult readQQqunData(@RequestParam String qq,HttpServletRequest request){
		WebResult result = new WebResult();
		try {
			result.setData(qqService.readQQDatas(qq ,request));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		}  catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/qqs/nickname/{nickname}",method=RequestMethod.GET)
	//通过QQ昵称得到群信息
	public WebResult readQQNickData(String nickname,String scrollId, int rowNumPerPage,HttpServletRequest request){
		LOG.info("qq nickname : {}", nickname);
		WebResult result = new WebResult();
		try {
			result.setData(qqService.readQQNickData(nickname,scrollId,rowNumPerPage, request));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		}  catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/quns/{qun}/members",method=RequestMethod.GET)
	//根据QQ群号得到群信息
	public WebResult readQQqunDatas(@RequestParam String qun,HttpServletRequest request){
		WebResult result = new WebResult();
		try {
			result.setData(qqService.readQQqunDatas(qun ,request));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
}
