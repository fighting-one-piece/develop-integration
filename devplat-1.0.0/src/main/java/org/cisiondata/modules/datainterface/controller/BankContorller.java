package org.cisiondata.modules.datainterface.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.IBankService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class BankContorller {
	@Resource(name="bankService")
	private IBankService bankService = null;
	@ResponseBody
	@RequestMapping(value="/bankcards/mobile/{mobile}")
	public WebResult readBankCard(@PathVariable String mobile){
		WebResult result = new WebResult();
		try {
			Map<String, Object> mapdata = bankService.readBankPhone(mobile);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(mapdata);
		}catch(BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
		}  catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	
	//  银行卡消费信息查询1
			@ResponseBody
			@RequestMapping(value = "/bankcards/{bankcard}",method = RequestMethod.GET)
			public WebResult queryQuota1(@PathVariable String bankcard) {
				WebResult result = new WebResult();
				try {
					Map<String, String> data = bankService.readqueryQuota1(bankcard);
					result.setCode(ResultCode.SUCCESS.getCode());
					result.setData(data);
				}catch (BusinessException bu){
					result.setCode(ResultCode.NOT_FOUNT_DATA.getCode());
					result.setFailure(bu.getMessage());
				}catch (Exception e) {
					result.setCode(ResultCode.FAILURE.getCode());
					result.setFailure(e.getMessage());
				}
				return result;
			}
}
