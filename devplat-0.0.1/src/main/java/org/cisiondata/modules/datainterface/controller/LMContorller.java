package org.cisiondata.modules.datainterface.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.service.ILMInternetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LMContorller {

	@Resource(name = "lmInternetService")
	private ILMInternetService lmInternetService = null;

	// 37、学历查询（D机构）
	@ResponseBody
	@RequestMapping(value = "/education/organizeD")
	public WebResult bbs(String idCard, String name) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.education_organizeD(idCard, name);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/education", method = RequestMethod.GET)
	public String lemonEducation() {
		return "lemoncome/lemoneducation";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/blacklistsearch", method = RequestMethod.GET)
	public ModelAndView blacklistsearch() {
		return new ModelAndView("lemoncome/blacklistsearch");
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/blacklistLoanPlatform", method = RequestMethod.GET)
	public ModelAndView lemonBlackList() {
		return new ModelAndView("lemoncome/blacklist");
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/plat", method = RequestMethod.GET)
	public String multPlatfrom() {
		return "lemoncome/multiplatfrom";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "overdue", method = RequestMethod.GET)
	public String loanOverdue() {
		return "lemoncome/loanoverdue";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "blacklist", method = RequestMethod.GET)
	public String blackListCheat() {
		return "lemoncome/blacklistcheat";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/internetNegative", method = RequestMethod.GET)
	public String lemonNegative() {
		return "lemoncome/internetNegative";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonGamblingDrug")
	public String nameidcheck() {
		return "lemoncome/lemonGamblingDrug";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/repeatedlyInquire", method = RequestMethod.GET)
	public String lemonInquire() {
		return "lemoncome/repeatedlyInquire";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/repeatedlyInquireA", method = RequestMethod.GET)
	public String lemonInquireA() {
		return "lemoncome/repeatedlyInquireA";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/P2POverdue", method = RequestMethod.GET)
	public String P2P() {
		return "lemoncome/P2POverdue";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/CourtEnforce", method = RequestMethod.GET)
	public String CourtEnforce() {
		return "lemoncome/CourtEnforce";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/phoneTagQuery", method = RequestMethod.GET)
	public String phoneTagQuery() {
		return "lemoncome/phoneTagQuery";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/addressProve", method = RequestMethod.GET)
	public String address() {
		return "lemoncome/addressProve";
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
			System.err.println(data + "---data");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/baiduQuery", method = RequestMethod.GET)
	public String baiduIntegralQuery() {
		return "lemoncome/baiduFinancialQuery";
	}

	// 22申请数据查询
	@ResponseBody
	@RequestMapping(value = "/query/apply")
	public WebResult dataFind(String phone, String idCard, String name, String bankCard) {
		System.err.println("进入22");
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readappKeys(phone, idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/dataApply")
	public String applyDatascats() {
		return "lemoncome/ApplyDatas";
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
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/Blacklistcat")
	public String blacklistcat() {
		return "lemoncome/blacklistion";
	}

	// 20可疑人员信息查询
	@ResponseBody
	@RequestMapping(value = "/suspiciousPersonscation")
	public WebResult SuspiciousPerson(String phone, String idCard, String staffType) {
		System.err.println(20);
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readCardUtil(phone, idCard, staffType);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "suspiciouscat")
	public String SuspiciousPersons() {
		return "lemoncome/suspiciousPersons";
	}

	// 19欺诈案件信息查询
	@ResponseBody
	@RequestMapping(value = "/FraudulentInformation/InformationCation")
	public WebResult reportInfoQuery(String phone, String idCard, String caseType) {
		System.err.println(19);
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readCaseReportInfo(phone, idCard, caseType);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "Fraudulentcat")
	public String fraudulentInFormation() {
		return "lemoncome/FraudulentInformation";
	}

	// 18 没做

	// 17、逾期短信信息查询
	@ResponseBody
	@RequestMapping(value = "/verify/phoneIsInBlacklist")
	public WebResult verifyPhoneIsInBlacklist(String phone) {
		System.err.println(phone + "--19");
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.phoneIsInBlacklist(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "verify")
	public String phoneIsInBlacklist() {
		return "lemoncome/PhoneIsInBlacklist";
	}

	// 16、通信小号

	// 15、手机号所属运营商查询
	@ResponseBody
	@RequestMapping(value = "/phone/OperatorNameQuery")
	public WebResult mobileNameQuery(String phone) {
		System.err.println(1111);
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readIdPhoto(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/NameQueryinfo", method = RequestMethod.GET)
	public String lemonPhone() {
		return "lemoncome/mobileOperatorNameQuery";
	}

	// 14、手机号绑定银行卡信息查询
	@ResponseBody
	@RequestMapping(value = "/mobile/operatorNameQuery")
	public WebResult queryPhoneCardBindInfo(String phone) {
		System.err.println(1111);
		WebResult result = new WebResult();
		try {
			Map<String, Map<String, String>> data = lmInternetService.readPhoto(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/phoneBankCardinfo", method = RequestMethod.GET)
	public String queryPhoneBankCardBindInfo() {
		return "lemoncome/phoneBankCardBindInfo";
	}

	// 13、手机号绑定银行卡账动信息查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phoneactivecation")
	public WebResult photoQuery(String phone) {
		System.err.println(13);
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readPhotoQuery(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonphoneactive", method = RequestMethod.GET)
	public String lemonPhoneActive() {
		return "lemoncome/lemonPhoneActive";
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
			System.err.println(data + "---data" + "```");
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonphoneinfo", method = RequestMethod.GET)
	public String readQueryLoadInfo() {
		return "lemoncome/lemonPhoneLoadInfo";
	}

	// 11、手机号绑定银行卡出入账查询(只支持移动)
	@ResponseBody
	@RequestMapping(value = "/phoneinfocation/query")
	public WebResult photoQuery1(String phone) {
		System.err.println(11);
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.readIdPhotoAccount(phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonphonelines", method = RequestMethod.GET)
	public String photoQuery1() {
		return "lemoncome/lemonPhoneLines";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonphonetime", method = RequestMethod.GET)
	public String lemonPhone1() {
		return "lemoncome/lemonPhoneTime";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/lemonphone", method = RequestMethod.GET)
	public String lemonPhone2() {
		return "lemoncome/lemonPhone";
	}

	// 8、身份证照片查询
	@ResponseBody
	@RequestMapping(value = "/idphotocation")
	public WebResult getIdPhoto(String name, String idCard) {
		System.err.println(8);
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.getIdPhoto(name, idCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/idphoto", method = RequestMethod.GET)
	public String lemonIdPhoto() {
		return "lemoncome/lemonIdPhoto";
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
//			result.setCode(ResultCode.FAILURE.getCode());
//			result.setFailure(e.getMessage());
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
		System.err.println(6);
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.bankcard2item(bankCard, phone);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "nameidcat")
	public String bankcard2item() {
		return "lemoncome/namePhoneId";
	}

	// 5、姓名-身份证号-照片三维校验
	@ResponseBody
	@RequestMapping(value = "/nameIDPhoto3Dcation")
	public WebResult photo3Dcation(String idCard, String name, String image) {
		System.err.println(5);
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.idNameImageCheck(idCard, name, image);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "nameIDPhoto3Dcat")
	public String idNameImageCheck() {
		return "lemoncome/nameIDphoto3D";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "nameidphonebankcat")
	public String nameIdphone() {
		return "lemoncome/nameIdphoneBanks";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "namephonection")
	public String idNamePhoneCheck() {
		return "lemoncome/nameIDs";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "nameIdCardAccountVerify")
	public String nameIdCardAccountVerify() {
		return "lemoncome/nameIdCardAccountVerify";
	}

	@ResponseBody
	@RequestMapping(value = "/nameIdCard/AccountVerifyM")
	public WebResult nameIdCardAccountVerifyM(String idCard, String name, String bankCard) {
		WebResult result = new WebResult();
		try {
			Map<String, String> data = lmInternetService.nameIdCardAccountVerifyM(idCard, name, bankCard);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "accountVerifyM")
	public String AccountVerifyM() {
		return "lemoncome/nameIdCardAccountVerifyM";
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
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/checknameid")
	public String idNameCheck() {
		return "lemoncome/NameIdCheck";
	}
	
	@RequestMapping(value="lemoncome",method=RequestMethod.GET)
	public String lemon() {
		return "lemoncome/lemon";
	}
	
	
	// 众多接口
	@ResponseBody
	@RequestMapping(value = "/relational/query")
	public WebResult relationalQquery(String type, String index) {
		System.out.println(type+"---type"+index+"----index");
		WebResult result = new WebResult();
		try {
			Object data = lmInternetService.relational_query(type, index);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData(data);
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//众多接口
	@RequestMapping(value="RelationalQuery",method=RequestMethod.GET)
	public String RelationalQuery() {
		return "lemoncome/RelationalQuery";
	}
	
}
