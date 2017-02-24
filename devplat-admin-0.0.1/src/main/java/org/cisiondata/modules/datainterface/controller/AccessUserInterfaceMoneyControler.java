package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.entity.AccessUserInterfaceMoney;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccessUserInterfaceMoneyControler {
	private Logger LOG = LoggerFactory.getLogger(AccessUserInterfaceMoneyControler.class);
	
	@Resource(name = "accessUserInterfaceMoneyService")
	private IAccessUserInterfaceMoneyService accessUserInterfaceMoneyService;
	
	//通过userInterfaceId查看相关数据
	@RequestMapping("access/findAccessMoney")
	@ResponseBody
	public WebResult findAccessUserInterfaceMoney(Long userInterfaceId){
		WebResult result = new WebResult();
		try {
			result.setData(accessUserInterfaceMoneyService.findAccessUserInterfaceMoney(userInterfaceId));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//修改
	@RequestMapping("access/updateAccessMoney")
	@ResponseBody
	public WebResult updateAccessUserInterfaceMoney(Long id,Long userInterfaceId,int responseCode,Double money){
		System.out.println(responseCode);
		WebResult result = new WebResult();
		try {
			accessUserInterfaceMoneyService.updateAccessUserInterfaceMoney(id,userInterfaceId,responseCode,money);
			result.setData("更新成功");
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//增加
	@RequestMapping("access/addAccessMoney")
	@ResponseBody
	public WebResult addAccessUserInterfaceMoney(AccessUserInterfaceMoney access){
		WebResult result = new WebResult();
		try {
			accessUserInterfaceMoneyService.addAccessUserInterfaceMoney(access);
			result.setData("增加成功");
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	//修改删除标示
		@RequestMapping("access/deleteAccessMoney")
		@ResponseBody
		public WebResult deleteAccessUserInterfaceMoney(Long userInterfaceId,int responseCode,boolean deleteFlag){
			WebResult result = new WebResult();
			try {
				accessUserInterfaceMoneyService.deleteAccessUserInterfaceMoney(userInterfaceId,responseCode,deleteFlag);
				result.setData("更新成功");
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
//				result.setFailure(e.getMessage());
				LOG.error(e.getMessage(), e);
			}
			return result;
		}
}
