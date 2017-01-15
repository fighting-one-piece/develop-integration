package org.cisiondata.modules.datainterface.service;

import java.util.Map;

import org.cisiondata.utils.exception.BusinessException;


public interface ILMInternetService {
	//众多接口
	public String relational_query(String type,String index) throws BusinessException;
	
	//46、反欺诈黑名单验证
	public Map<String, String> auditphone(String phone) throws BusinessException;
	
	//38、信贷综合信息查询
	public Map<String, String> readLoanInfo(String phone) throws BusinessException;
	
	//37、学历查询（D机构）
	public Map<String, String> education_organizeD(String idCard, String name) throws BusinessException;
	
	//36、搜索黑名单blacklist_search
	public Map<String, Map<String, String>> blacklist_search(String idCard,String name,String phone) throws BusinessException;
	
	//35数信网黑名单
	public  Map<String, String> readBlackList(String idCard,String name,String phone) throws BusinessException;
	
	//34多次申请记录查询C
	public Map<String, String> readMultiPlatform(String phone) throws BusinessException;
	
	//33网络公开逾期信息
	public Map<String, String> readLoanOverdue(String idCard, String phone) throws BusinessException;
	
	//32 合作机构共享黑名单
	public Map<String, String> readBlacklistCheat(String idCard,String name,String phone) throws BusinessException;
	
	//31、网络公开负面信息
	public  Map<String, String> readInternetNegative(String phone) throws BusinessException;
	
	//30赌博吸毒名单
	public Map<String, String> blacklistGamble(String phone) throws BusinessException;
	
	//29 多次申请记录查询B
	public Map<String, String> repeatedlyInquireB(String idCard,String name, String phone) throws BusinessException;
	
	//28 多次申请记录查询A
	public Map<String, String> repeatedlyInquireA(String idCard,String name, String phone) throws BusinessException;
	
	//27 银行、P2P逾期记录
	public Map<String, String> bankP2P(String idCard,String name, String phone) throws BusinessException;
	
	//26法院被执行人记录
	public Map<String, String> CourtExecutePeople(String idCard,String name, String phone,String idType,String gender) throws BusinessException;
	
	//25手机标签查询
	public Map<String, String> phoneTagQuery(String phone) throws BusinessException;
	
	//24地址验证readPhoto
	public Map<String, String> addresVerification(String idCard,String name,String phone,String idType,String homeCity,String homeAddress,String companyCity,String companyAddress) throws BusinessException;

	//23百度消费金融评分查询
	public Map<String, Map<String, String>> baiduQuery(String phone,String name,String idCard,String maritalStatus,String degree,String homeAddress,String companyAddress) throws BusinessException;
	
	//22申请数据查询
	public Map<String, String> readappKeys(String phone,String idCard,String name,String bankCard) throws BusinessException;

	//21柠檬黑名单查询
	public  Map<String, String> readCardUtil(String phone, String idCard, String name, String bankCard) throws BusinessException;
	
	//20可疑人员信息查询 
	public Map<String, Map<String, String>> readCardUtil(String phone, String idCard, String staffType) throws BusinessException;
	
	//19欺诈案件信息查询 
	public Map<String, Map<String, String>> readCaseReportInfo(String phone, String idCard, String caseType) throws BusinessException;
	
	//18 没做
	
	//17、逾期短信信息查询 
	public Map<String, Map<String, String>> phoneIsInBlacklist(String phone) throws BusinessException;
	
	//16、通信小号 
	
	//15、手机号所属运营商查询 
	public Map<String, Map<String, String>> readIdPhoto(String phone) throws BusinessException;
	
	//14、手机号绑定银行卡信息查询(只支持移动)
	public Map<String, Map<String, String>> readPhoto(String phone) throws BusinessException;
	
	//13、手机号绑定银行卡账动信息查询(只支持移动)
	public Map<String, String> readPhotoQuery(String phone) throws BusinessException;
	
	//12、手机号绑定银行卡还款情况查询(只支持移动)
	public Map<String, String> readQueryLoadInfo(String phone) throws BusinessException;
	
	//11、手机号绑定银行卡出入账查询(只支持移动) 
	public  Map<String, String> readIdPhotoAccount(String phone) throws BusinessException;
	
	//10、手机号当前状态查询(只支持移动)
	public Map<String, String> mobileStatusQuery(String phone) throws BusinessException;
	
	//9、手机号在网时长查
	public Map<String, String> mobileOnlineIntervalQuery(String phone) throws BusinessException;
	
	//8、身份证照片查询 
	public  Map<String, String> getIdPhoto(String name, String idCard) throws BusinessException;
	
//	//7、学历查询 
//	public Map<String, String> verify_education(String idCard,String name,String levelNo);
	
	//6、姓名-银行卡号一致性校验 
	public Map<String, String> bankcard2item(String bankCard,String phone) throws BusinessException;
	
	//5、姓名-身份证号-照片三维校验
	public Map<String, String> idNameImageCheck(String idCard,String name,String image) throws BusinessException;

	//4、姓名-身份证号-手机号-银行卡号一致性校验 
	public Map<String, String> bankcard4item(String name, String bankCard, String idCard, String phone) throws BusinessException;
	
	//3、姓名-身份证号-手机号一致性校验 
	public Map<String, String> idNamePhoneCheck(String idCard, String name, String phone) throws BusinessException;
	
	//2、姓名-身份证号-银行卡号一致性校验
	public Map<String, String> nameIdCardAccountVerify(String idCard, String name, String bankCard) throws BusinessException;
	public Map<String, String> nameIdCardAccountVerifyV(String idCard, String name, String bankCard) throws BusinessException;
	public Map<String, String> nameIdCardAccountVerifyF(String idCard, String name, String bankCard) throws BusinessException;
	
	//1、姓名-身份证号一致性校验
	public Map<String, String> idNameCheck(String idCard, String name) throws BusinessException;
	
}
