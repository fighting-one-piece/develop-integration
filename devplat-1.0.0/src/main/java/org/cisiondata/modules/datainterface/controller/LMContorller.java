package org.cisiondata.modules.datainterface.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.ILMInternetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LMContorller {

	@Resource(name = "lmInternetService")
	private ILMInternetService lmInternetService = null;
	
	//11-14接口整合
	@ResponseBody
	@RequestMapping(value="/query/phoneBank")
	public WebResult readBankCard(String phone){
		WebResult result = new WebResult();
		try {
			Map<String, Object> mapdata = lmInternetService.readBankPhone(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(mapdata);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	//40. 法院被执行人查询（B机构）
	@ResponseBody
	@RequestMapping(value = "/court/executedPeopleB")
	public WebResult courtexecutedPeopleB(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.court_executedPeopleB(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	//39、学历查询（F机构）
	@ResponseBody
	@RequestMapping(value = "/education/organizeF")
	public WebResult organizeF(String idCard, String name) {
		System.err.println(idCard+"----idCard--"+name+"--name--");
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.education_organizeF(idCard, name);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	// 38、信贷综合信息查询
	@ResponseBody
	@RequestMapping(value = "/loan/info")
	public WebResult loaninfo(String phone){
		WebResult result = new WebResult();
		try {
			Map<String, String> map = lmInternetService.readLoanInfo(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(map);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	//46、反欺诈黑名单验证
	@ResponseBody
	@RequestMapping(value = "/audit/phone")
	public WebResult bbs(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.auditphone(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	// 37、学历查询（D机构）
	@ResponseBody
	@RequestMapping(value = "/education/organizeD")
	public WebResult educationorganizeD(String idCard, String name) {
		WebResult result = new WebResult();
		try {
			Map<String, Object> data = lmInternetService.education_organizeD(idCard, name);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	

	//36、搜索黑名单
	@ResponseBody
	@RequestMapping(value = "/blacklist/search")
	public WebResult asd(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		System.out.println(36);
		try {
			Map<String, Map<String, String>> data = lmInternetService.blacklist_search(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
	
	// 35数信网黑名单
	@ResponseBody
	@RequestMapping(value = "/lemonblacklist/blacklistdata")
	public WebResult blacklistLoan(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readBlackList(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 34多次申请记录查询C
	@ResponseBody
	@RequestMapping(value = "/multiplatfrom/multi")
	public WebResult readMulit(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readMultiPlatform(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 33网络公开逾期信息
	@ResponseBody
	@RequestMapping(value = "/loanOverdue/blackList")
	public WebResult loanOverdue(String idCard, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readLoanOverdue(idCard, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 32 合作机构共享黑名单
	@ResponseBody
	@RequestMapping(value = "/blackListCheat/cheat")
	public WebResult blackListCheat(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readBlacklistCheat(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 31网络负面信息
	@ResponseBody
	@RequestMapping(value = "/internet/negative")
	public WebResult internetNegative(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readInternetNegative(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 30赌博吸毒名单
	@ResponseBody
	@RequestMapping(value = "/gambing/dru")
	public WebResult dru(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.blacklistGamble(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 29 多次申请记录查询 B
	@ResponseBody
	@RequestMapping(value = "/select/recording")
	public WebResult selectRecording(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.repeatedlyInquireB(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 28 多次申请记录查询 A
	@ResponseBody
	@RequestMapping(value = "/select/recordingA")
	public WebResult selectRecordingA(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.repeatedlyInquireA(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	// 27 银行、 P2P 逾期记录
	@ResponseBody
	@RequestMapping(value = "/p2p/overdue")
	public WebResult P2POverdue(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.bankP2P(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 26法院被执行人记录
	@ResponseBody
	@RequestMapping(value = "/court/execute")
	public WebResult CourtExecute(String idCard, String name, String phone, String idType, String gender) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.CourtExecutePeople(idCard, name, phone, idType, gender);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 25 手机号标签查询
	@ResponseBody
	@RequestMapping(value = "/phone/query")
	public WebResult phoneTagQuerys(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.phoneTagQuery(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 24地址验证
	@ResponseBody
	@RequestMapping(value = "/address/verification")
	public WebResult addressProve(String idCard, String name, String phone, String idType, String homeCity,
			String homeAddress, String companyCity, String companyAddress) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.addresVerification(idCard, name, phone, idType, homeCity,
					homeAddress, companyCity, companyAddress);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 23 百度消费金融评分查询
	@ResponseBody
	@RequestMapping(value = "/baidu/integralQuery")
	public WebResult baiduIntegralQuery(String phone, String name, String idCard, String maritalStatus, String degree,
			String homeAddress, String companyAddress) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.baiduQuery(phone, name, idCard, maritalStatus,
					degree, homeAddress, companyAddress);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 22申请数据查询
	@ResponseBody
	@RequestMapping(value = "/query/apply")
	public WebResult dataFind(String phone, String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readappKeys(phone, idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 21柠檬黑名单查询
	@ResponseBody
	@RequestMapping(value = "/Blacklist/Blacklistcation")
	public WebResult blacklistcation(String phone, String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readCardUtil(phone, idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 20可疑人员信息查询
	@ResponseBody
	@RequestMapping(value = "/suspiciousPersonscation")
	public WebResult SuspiciousPerson(String phone, String idCard, String staffType) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readCardUtil(phone, idCard, staffType);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 19欺诈案件信息查询
	@ResponseBody
	@RequestMapping(value = "/FraudulentInformation/InformationCation")
	public WebResult reportInfoQuery(String phone, String idCard, String caseType) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readCaseReportInfo(phone, idCard, caseType);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}



	// 18、 银行卡消费信息查询1
		@ResponseBody
		@RequestMapping(value = "/query/quota1")
		public WebResult queryQuota1(String bankCard, String idCard, String phone, String name) {
			WebResult result = new WebResult();
			try {
				Map<String, Map<String, String>> data = lmInternetService.readqueryQuota1(bankCard, idCard, phone, name);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(data);
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
		// 18、 银行卡消费信息查询2
		@ResponseBody
		@RequestMapping(value = "/query/quota2")
		public WebResult queryQuota2(String bankCard, String idCard, String phone, String name) {
			WebResult result = new WebResult();
			try {
				Map<String, Map<String, String>> data = lmInternetService.readqueryQuota2(bankCard, idCard, phone, name);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(data);
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
		// 18、 银行卡消费信息查询3
		@ResponseBody
		@RequestMapping(value = "/query/quota3")
		public WebResult queryQuota3(String bankCard, String idCard, String phone, String name) {
			WebResult result = new WebResult();
			try {
				Map<String, Map<String, String>> data = lmInternetService.readqueryQuota3(bankCard, idCard, phone, name);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(data);
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
		// 18、 银行卡消费信息查询4
		@ResponseBody
		@RequestMapping(value = "/query/quota4")
		public WebResult queryQuota4(String bankCard, String idCard, String phone, String name) {
			WebResult result = new WebResult();
			try {
				Map<String, Map<String, String>> data = lmInternetService.readqueryQuota4(bankCard, idCard, phone, name);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(data);
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
		// 18、 银行卡消费信息查询5
		@ResponseBody
		@RequestMapping(value = "/query/quota5")
		public WebResult queryQuota5(String bankCard, String idCard, String phone, String name) {
			WebResult result = new WebResult();
			try {
				Map<String, Map<String, String>> data = lmInternetService.readqueryQuota5(bankCard, idCard, phone, name);
				result.setCode(ResultCode.SUCCESS.getCode());
				result.setData(data);
			} catch (Exception e) {
				result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
				result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
			}
			return result;
		}
		
		@RequestMapping(value = "queryQuota")
		public String queryquota() {
			return "lemoncome/queryQuota";
		}

	// 17、逾期短信信息查询
	@ResponseBody
	@RequestMapping(value = "/verify/phoneIsInBlacklist")
	public WebResult verifyPhoneIsInBlacklist(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.phoneIsInBlacklist(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 16、通信小号

	// 15、手机号所属运营商查询
	@ResponseBody
	@RequestMapping(value = "/phone/OperatorNameQuery")
	public WebResult mobileNameQuery(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readIdPhoto(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 14、手机号绑定银行卡信息查询
	@ResponseBody
	@RequestMapping(value = "/mobile/operatorNameQuery")
	public WebResult queryPhoneCardBindInfo(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readPhoto(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 13、手机号绑定银行卡账动信息查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phoneactivecation")
	public WebResult photoQuery(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readPhotoQuery(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 12、手机号绑定银行卡还款情况查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phoneinfocation")
	public WebResult readQueryLoadInfo(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readQueryLoadInfo(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 11、手机号绑定银行卡出入账查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phoneinfocation/query")
	public WebResult photoQuery1(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readIdPhotoAccount(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 10、手机号当前状态查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phonecation/query")
	public WebResult photoQuery2(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.mobileStatusQuery(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 9、手机号在网时长查
	@ResponseBody
	@RequestMapping(value = "/mobile/OnlineIntervalQuery")
	public WebResult photoQuery3(String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.mobileOnlineIntervalQuery(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 8、身份证照片查询
	@ResponseBody
	@RequestMapping(value = "/idphotocation")
	public WebResult getIdPhoto(String name, String idCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.getIdPhoto(name, idCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 7、学历查询
//	@ResponseBody
//	@RequestMapping(value = "/lemoneducation")
//	public WebResult education(String idCard, String name, String levelNo) {
//		System.err.println(7);
//		WebResult result = new WebResult();
//		try {
//			Map<String, String> data = lmInternetService.verify_education(idCard, name, levelNo);
//			result.setCode(ResultCode.SUCCESS.getCode());
//			result.setData(data);
//		} catch (Exception e) {
//			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
//			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
//		}
//		return result;
//	}
//
//	@RequestMapping(value = "/education", method = RequestMethod.GET)
//	public String lemonEducation() {
//		return "lemoncome/lemoneducation";
//	}

	// 6、姓名-银行卡号一致性校验
	@ResponseBody
	@RequestMapping(value = "/bankcard/item")
	public WebResult bankcardItem(String bankCard, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.bankcard2item(bankCard, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 5、姓名-身份证号-照片三维校验
	@ResponseBody
	@RequestMapping(value = "/nameIDPhoto3Dcation")
	public WebResult photo3Dcation(String idCard, String name, String image) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.idNameImageCheck(idCard, name, image);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 4、姓名-身份证号-手机号-银行卡号一致性校验
	@ResponseBody
	@RequestMapping(value = "/bankcard/4item")
	public WebResult nameIdphoneBank(String name, String bankCard, String idCard, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.bankcard4item(name, bankCard, idCard, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 3、姓名-身份证号-手机号一致性校验
	@ResponseBody
	@RequestMapping(value = "/idNamePhoneCheck")
	public WebResult namePhoneCheck(String idCard, String name, String phone) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.idNamePhoneCheck(idCard, name, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 2、姓名-身份证号-银行卡号一致性校验

	@ResponseBody
	@RequestMapping(value = "/nameIdCard/AccountVerify")
	public WebResult nameIdCard(String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.nameIdCardAccountVerify(idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 2、M姓名-身份证号-银行卡号一致性校验
	@ResponseBody
	@RequestMapping(value = "/nameIdCard/AccountVerifyM")
	public WebResult nameIdCardAccountVerifyV(String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.nameIdCardAccountVerifyV(idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}


	// 2、F姓名-身份证号-银行卡号一致性校验
	@ResponseBody
	@RequestMapping(value = "/nameIdCard/AccountVerifyF")
	public WebResult nameIdCardAccountVerifyF(String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.nameIdCardAccountVerifyF(idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	// 1、姓名-身份证号一致性校验
	@ResponseBody
	@RequestMapping(value = "/idName/Check")
	public WebResult asd(String idCard, String name) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.idNameCheck(idCard, name);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}

	
	
	// 众多接口
	@ResponseBody
	@RequestMapping(value = "/relational/query")
	public WebResult relationalQquery(String type, String index) {
		WebResult result = new WebResult();
		try {
			Object data = lmInternetService.relational_query(type, index);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.SYSTEM_IS_BUSY.getCode());
			result.setFailure(ResultCode.SYSTEM_IS_BUSY.getDesc());
		}
		return result;
	}
	
}
