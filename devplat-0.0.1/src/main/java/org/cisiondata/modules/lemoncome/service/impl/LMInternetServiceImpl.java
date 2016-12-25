package org.cisiondata.modules.lemoncome.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.cisiondata.modules.lemoncome.Constants;
import org.cisiondata.modules.lemoncome.service.ILMInternetService;
import org.cisiondata.utils.http.HttpUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("lmInternetService")
public class LMInternetServiceImpl implements ILMInternetService{
	
	/**
	 * 37、学历查询（D机构）
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> education_organizeD(String idCard, String name){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		System.out.println(name+"--"+idCard+"--");
		System.out.println(1);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161210143154664llCfIKOZtUbURuT\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/education/organizeD", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			System.out.println(sss);
			if(sss==200){
				data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 35数信网黑名单
	 * @return 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, String> readBlackList(String idCard,String name,String phone) {
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		System.err.println(35);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213150210248TjNXqhNNDWKARPr\",\"data\":{\"result\":{\"content\":[{\"desc\":\"命中次数 1\",\"originalRet\":{\"app_mobile\":\"\",\"message\":\"网络公开负面信息\",\"home\":\"江西-南昌\",\"hit_num\":1,\"sex\":\"女\",\"app_qq\":\"\",\"hit_list\":[{\"publish_date\":\"2014-12-15\",\"source_url\":\"http://s.digcredit.com/urls/10020/9757709dbc7cb97a47e1c2def585b7da/\",\"province\":\"\",\"court\":\"\",\"file_time\":\"\",\"overdue_amount\":4980.05,\"comp_name\":\"武汉商贸职业学院\"}],\"age\":29,\"xingzuo\":\"天蝎座\",\"app_id_card\":\"36012119861115294X\",\"type\":\"C01\",\"app_name\":\"潘丽\"},\"type\":\"网络公开负面信息\",\"date\":\"\"}]}}}";
		try {
			//调用数信网黑名单API
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	
	/**
	 * 34多次申请记录查询C
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readMultiPlatform(String phone) {
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(34);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213164108481HkYTOaePyhlATJm\",\"data\":{\"result\":{\"content\":[{\"desc\":\"【图】- 工行逸贷,pos机招商,办理- 邯郸邯郸担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 个人无抵押,无担保小额贷款,1-3天放款! - 邯郸邯郸担保/贷款...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"xiao13403307888_借款列表_个人主页_个人小额短期无抵押贷款_...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邢台邢台担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邯郸邯郸担保/贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 安阳安阳新区担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"...xiao13403307888的借款_个人小额短期无抵押贷款_P2P网..._拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【累积信用】xiao13403307888的首次借款—wap专享_ ... - 拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【网贷体验】听说网上能贷款，体验下网贷_xiao13403307888的借款_ ...\",\"type\":\"贷款相关\",\"date\":\"\"}]}}}";
		try {
			//调用多次申请记录查询API
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/loan/multiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	
	/**
	 * 33 网络公开逾期信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readLoanOverdue(String idCard, String phone) {
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard",idCard );
		map.put("phone", phone);
		System.out.println(33);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213174041396mDJfxNsgcXXSGTt\",\"data\":{\"result\":{\"content\":[{\"desc\":\"王龙\",\"originalRet\":{\"black_del_max_days\":\"43\",\"court_name\":\"\",\"black_source\":\"拍拍贷\",\"black_user_id\":\"\",\"disrupt_type_name\":\"\",\"crawl_time\":\"2015-08-06\",\"unperform_part\":\"\",\"black_home_addr\":\"\",\"black_user_mobile\":\"13986679***\",\"id\":\"118077\",\"mobile_equipment_number\":\"\",\"level\":\"1\",\"black_company_addr\":\"\",\"black_user_name\":\"王龙\",\"duty\":\"\",\"performance\":\"\",\"user_case_code\":\"\",\"qq\":null,\"black_user_gender\":\"0\",\"black_update_time\":\"0000-00-00\",\"publish_date\":\"\",\"user_type\":\"\",\"performed_part\":\"\",\"black_overdue_amt\":\"0.00\",\"black_user_phone\":\"\",\"black_del_cnt\":\"0\",\"black_company_name\":\"\",\"black_loan_amt\":\"0.00\",\"source_mobile\":\"13986679000\",\"black_email\":\"\"},\"date\":\"0000-00-00\",\"type\":\"拍拍贷\"}]}}}";
		try {
			//调用网络公开逾期信息API
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanOverdue", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
		
	}
	
	/**
	 * 32 合作机构共享黑名单
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readBlacklistCheat(String idCard,String name,String phone) {
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard",idCard );
		map.put("name", name);
		map.put("phone", phone);
		System.out.println(32);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213181030680ffGuAlglZbXRqIC\",\"data\":{\"result\":{\"content\":[{\"desc\":\"欺诈申请\",\"originalRet\":{\"loan_type\":\"\",\"name\":\"100\",\"confirm_type\":\"欺诈\",\"pid\":\"100\",\"applied_at\":\"\",\"confirm_details\":\"欺诈申请\",\"mobile\":\"0\",\"confirmed_at\":\"2012年08月01日\"},\"type\":\"欺诈\",\"date\":\"\"}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/cheat", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
		
	}
	
	/**
	 * 31网络公开负面信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readInternetNegative(String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(31);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216205655422RXmkXYUMaXWRUqL\",\"data\":{\"result\":{\"content\":[{\"desc\":\"<a href=\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\" style=\"color:#E3AD1C\"  target=\"_blank\">【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网</a>\",\"originalRet\":{\"abstract\":\"赖永超 18950848338 18950848338福建 35050019901020*闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573山西 14270319890323*西安高新科技职业学...\",\"title\":\"【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网\",\"no\":1,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\"},\"type\":\"黑名单\",\"date\":\"\"},{\"desc\":\"<a href=\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\" style=\"color:#E3AD1C\"  target=\"_blank\">贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼</a>\",\"originalRet\":{\"abstract\":\"2014年11月17日 - 赖永超 18950848338 18950848338 福建 350500199010205535 闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573 山西 142703198903232416 ...\",\"title\":\"贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼\",\"no\":11,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\"},\"type\":\"黑名单\",\"date\":\"\"}]}}}";
//		{"errorCode":200,"errorMsg":"请求成功","uid":"20161216205655422RXmkXYUMaXWRUqL","data":{"result":{"content":[{"desc":"<a href=\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\" style=\"color:#E3AD1C\"  target=\"_blank\">【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网</a>","originalRet":{"abstract":"赖永超 18950848338 18950848338福建 35050019901020*闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573山西 14270319890323*西安高新科技职业学...","title":"【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网","no":1,"class":"黑名单","search_word":"18950848338","search_type":"phone","url":"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou"},"type":"黑名单","date":""},{"desc":"<a href=\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\" style=\"color:#E3AD1C\"  target=\"_blank\">贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼</a>","originalRet":{"abstract":"2014年11月17日 - 赖永超 18950848338 18950848338 福建 350500199010205535 闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573 山西 142703198903232416 ...","title":"贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼","no":11,"class":"黑名单","search_word":"18950848338","search_type":"phone","url":"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq"},"type":"黑名单","date":""}]}}}
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	

	/**
	 * 30赌博吸毒名单
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> blacklistGamble(String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(30);
	    String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216112015658ZgRXWYjtmVRgpda\",\"data\":{\"result\":{\"content\":[{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201503期方案:100437150 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150202期方案:99727487 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150301期方案:100430851 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:97727442 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97595811 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:99712532 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150301期方案:100489788 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15015期方案:97557409 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97689257 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15031期方案:100284165 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150202期方案:99520485 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97751148 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150301期方案:100489785 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球混合过关201502期方案:97572774 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150103期方案:97029786 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98433767 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98405400 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:97530337 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:98185464 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"14场胜负彩2015018期方案：97507488 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"}]}}}";
		try {
//			String result = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/gamble", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson=new Gson();
			Map<String, Map<String, Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	
	
	/**
	 * 29多次申请记录查询B 数据为空
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> repeatedlyInquireB(String idCard,String name,String phone){
		System.err.println(idCard+"```"+name+"--"+phone+"------impl1");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		System.out.println(29);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220144236902PoRmGyqrosKGpeC\",\"data\":{\"result\":{\"content\":[{}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/multiApplyCheckBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
//				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 28多次申请记录查询A  接口有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> repeatedlyInquireA(String idCard,String name,String phone){
		System.err.println(idCard+"```"+name+"--"+phone+"------impl2");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		System.err.println(28);
//		String kk ="";
		try {
			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/debtMultiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
//				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 27 银行.p2p 预期记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> bankP2P(String idCard,String name,String phone){
		System.err.println(idCard+"```"+name+"--"+phone+"------impl3");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		System.err.println(27);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220154723835aDcCoriQFtiwJsE\",\"data\":{\"result\":{\"content\":[{\"Flag\":{\"specialList_c\":\"0\"},\"SpecialList_c\":{}}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/socialContact", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 26 法院被执行人记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> CourtExecutePeople(String idCard,String name,String phone,String idType,String gender){
		System.err.println(idCard+"```"+name+"--"+phone+"------impl4");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		map.put("idType", idType);
		map.put("gender", gender);
		System.err.println(26);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220162850089OIxXlqVjGgiyKpJ\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"id_type\":1,\"baidu_account\":\"\",\"discredit_records\":{\"netloan\":null,\"tax\":null,\"court\":null},\"real_name\":\"吴燕\",\"imei\":\"\",\"gender\":1,\"id_no\":\"610628197707020043\"}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanCredit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 25 手机号标签查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> phoneTagQuery(String phone){
		System.err.println(phone+"------impl5");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.err.println(25);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220165426378ODcpeCEOyYHRwrh\",\"data\":{\"result\":{\"content\":[{\"tags\":[{\"count\":\"9\",\"tag_name\":\"济仁诊所\"}],\"phone\":\"13899787289\",\"com_address\":null,\"carrier\":\"新疆伊犁移动\",\"com_name\":null}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/phone/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 24地址验证
	 */ 
	@SuppressWarnings("unchecked")
	public Map<String, String> addresVerification(String idCard,String name,String phone,String idType,String homeCity,String homeAddress,String companyCity,String companyAddress){
		System.err.println(idCard+"```"+name+"--"+phone+"------impl6");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		map.put("idType", idType);
		map.put("homeCity", homeCity);
		map.put("homeAddress", homeAddress);
		map.put("companyCity", companyCity);
		map.put("companyAddress", companyAddress);
		System.err.println(24);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220172036886lEqAWUKXHwZQOtW\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"local_freq_address_list\":null,\"id_type\":1,\"baidu_account\":\"\",\"real_name\":\"吴燕\",\"imei\":\"\",\"company_address_list\":[{\"distance\":\"1252862.0\",\"verify_result\":\"D\"}],\"home_address_list\":null,\"nolocal_freq_address_list\":null,\"id_no\":\"610628197707020043\"}]}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/address/verify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 23 百度消费金融评分查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> baiduQuery(String phone,String name,String idCard,String maritalStatus,String degree,String homeAddress,String companyAddress){
		System.err.println(phone+"------百度消费");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("maritalStatus", maritalStatus);
		map.put("degree", degree);
		map.put("homeAddress", homeAddress);
		map.put("companyAddress", companyAddress);
		System.err.println(23);
		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220175529058pfzZttkXoltdKpm\",\"data\":{\"lemon_A_pd\":\"0.1721499860\",\"lemon_B_pd\":\"0.0820594504\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/credit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 22申请数据查询     接口有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readappKeys(String phone,String idCard,String name,String bankCard){
		System.err.println(phone+"------22");
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
		System.err.println(22);
		String kk ="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/applyDataInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("message");
				 System.err.println(data+"-----data");
			}
		}catch(Exception e){
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 21柠檬黑名单查询
	 */
	@SuppressWarnings({ "unchecked"})
	public  Map<String, String> readCardUtil(String phone, String idCard, String name, String bankCard) {
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("bankCard", bankCard);
		System.err.println(21);
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215202241570BwyLCmksqPGifZv\",\"data\":{\"hits\":\"0\",\"message\":\"未命中\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklistInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson=new Gson();
			Map<String,Map<String,String>> re = gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
			
		} catch (Exception e) {
			
		}
		return data;
	}
	
	/**
	 * 20可疑人员信息查询 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> readCardUtil(String phone, String idCard, String staffType) {
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		map.put("idCard", idCard);
		map.put("staffType", staffType);
		System.err.println(20+"lmp");
		String test="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215150250391mzwEJyTakXyQUpa\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/staffReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(test, Map.class);
			Map<String,Object> gg=gson.fromJson(test,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 19、欺诈案件信息查询 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> readCaseReportInfo(String phone, String idCard, String caseType){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		map.put("idCard", idCard);
		map.put("caseType", caseType);
		System.err.println(19+"--lmp");
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161222165911932BAphTgQdwSHLIsV\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/caseReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 18 没做
	 */
	
	
	/**
	 * 17、逾期短信信息查询    返回值有问题 提示未知错误
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> phoneIsInBlacklist(String phone){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		System.out.println(phone);
		System.err.println(17+"--lmp");
		String kk="{\"errorCode\":407,\"errorMsg\":\"未知错误\",\"uid\":\"20161222173528244fNOPUjEuCJHmTFN\",\"data\":null}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/verifyPhoneIsInBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 16 、通信小号 
	 */
	
	/**
	 * 15、手机号所属运营商查询 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> readIdPhoto(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160912180520312vyCQuTQknMJQMVL\",\"data\":{ \"result\":\"1\", \"msg\":\"查询成功\",\"operatorType\": \"中国电信\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileOperatorNameQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 14、手机号绑定银行卡信息查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> readPhoto(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		System.out.println(14+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\": \"2012\",\"statusMsg\": {\"debitCardCount\": \"2\",\"creditCardCount\": \"0\",\"creditCardAging\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null},\"creditCardAge\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null}}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryPhoneBankCardBindInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 13、手机号绑定银行卡账动信息查询(只支持移动)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readPhotoQuery(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(13+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"paymentShortTermVoloatilityIndex\":\"0\",\"depositShortTermVoloatilityIndex\":\"-6\",\"depositLongTermVoloatilityIndex\":\"1\"}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryActive ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data").get("result");
				 System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 12、手机号绑定银行卡还款情况查询(只支持移动)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readQueryLoadInfo(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(12+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"本数据库中未查得\",\"result\":{\"currentOutstandingLoanAmount\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"currentOutstandingLoanCount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"}}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryLoadInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data").get("result");
				 System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 11、手机号绑定银行卡出入账查询(只支持移动) 
	 * @return 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readIdPhotoAccount(String phone){
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.err.println(11+"---");
		//测试数据  手机号绑定银行卡出入账查询
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"debitCardRemainingSum\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment3m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit3m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment12m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit12m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"}}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			//获取当前状态码
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss =  (int)Double.parseDouble(gg.get("errorCode").toString());
			// 获取返回码
			Map<String, Map<String, Object>> statusCode = gson.fromJson(kk, Map.class);
			int status = Integer.parseInt(statusCode.get("data").get("statusCode").toString());
			//获取返回结果
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			if(sss == 200){
				if(status == 2012){					
					data =re.get("data").get("result");
				}
			}
		} catch (Exception e) {
				e.getMessage();
		}
		return data;
	}
	
	/**
	 * 10、手机号当前状态查询(只支持移动)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> mobileStatusQuery(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		System.out.println(10+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212183028759SFNqBddicRrYXVO\",\"data\":{\"result\":\"1\",\"msg\":\"在网\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileStatusQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
				 System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 9、手机号在网时长查
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> mobileOnlineIntervalQuery(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212175927503EUbsfnUJYHXnZHD\",\"data\":{\"result\":\"1\",\"msg\":\"在网时长24个月以上\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileOnlineIntervalQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 8、身份证照片查询 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, String> getIdPhoto(String name, String idCard) {
		// name:吴绍国 idCard：511023197001213276
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("name", name);
		map.put("idCard", idCard);
		System.err.println(8+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212152955332fKVbNAVXbjRESdO\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\",\"idCardPhoto\":\"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\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/getIdPhoto", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson = new Gson();
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			Map<String, Map<String, String>> re = gson.fromJson(kk, Map.class);
			int code = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			// 取出结果
			String status = re.get("data").get("checkStatus");
			// 请求成功
			if (code == 200) {
				if(status.equals("S")){					
					data = re.get("data");
					System.err.println(data+"-----data````");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 7、学历查询     已停用
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> verify_education(String idCard,String name,String levelNo) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("levelNo", levelNo);
		System.out.println(7+"---");
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161210143154664llCfIKOZtUbURuT\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/verify_education", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			System.err.println(gg+"GG");
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data").get("checkResult");
				System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	/**
	 * 6、姓名-银行卡号一致性校验 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> bankcard2item(String bankCard,String name){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("name", name);
		map.put("bankCard", bankCard);
		System.err.println(6);
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223163314782ttGfPlOutECRxXD\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/bankcard2item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			String status = re.get("data").get("checkStatus");
			if(sss==200){
				if(status.equals("S")){					
					data = re.get("data");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 5、姓名-身份证号-照片三维校验
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> idNameImageCheck(String idCard,String name,String image){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("image", image);
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213175449320sCdBHzpDmXMMdHV\",\"data\":{\"result\":\"2\",\"msg\":\"身份证核查成功，但人脸比对失败\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNameImageCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 4、姓名-身份证号-手机号-银行卡号一致性校验 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> bankcard4item(String name, String bankCard, String idCard, String phone){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		System.out.println(4+"-------");
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223174421480OmRSluLOSWgTnmo\",\"data\":{\"checkStatus\":\"F\",\"message\":\"认证信息不匹配\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/bankcard4item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 3、姓名-身份证号-手机号一致性校验  接口有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> idNamePhoneCheck(String idCard, String name, String phone){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("phone", phone);
		System.out.println(name+"--"+idCard+"--"+phone+"--");
		System.out.println(3+"-------");
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223180719952cyPanxXCeORljrQ\",\"data\":{\"result\":\"1\",\"msg\":\"一致\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNamePhoneCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			System.out.println(sss);
			if(sss==200){
				data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 2、姓名-身份证号-银行卡号一致性校验
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> nameIdCardAccountVerify(String idCard, String name, String bankCard){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
		System.out.println(name+"--"+idCard+"--"+bankCard+"--");
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223202611272XKCZUKIyKukmgmW\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/nameIdCardAccountVerify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			System.out.println(sss);
			if(sss==200){
				data = re.get("data");
				System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	/**
	 * 2M 姓名-身份证号-银行卡号一致性校验    有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> nameIdCardAccountVerifyM(String idCard, String name, String bankCard){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
		System.out.println(name+"--"+idCard+"--"+bankCard+"--");
		String kk="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/nameIdCardAccountVerifyM", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			System.out.println(sss);
			if(sss==200){
				data = re.get("data");
				System.err.println(data+"-----data````");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	/**
	 * 1、姓名-身份证号一致性校验
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> idNameCheck(String idCard, String name){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		System.out.println(name+"--"+idCard+"--");
		System.out.println(1);
		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212174359875JqqwsAteydQGLxy\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\"}}";
		try {
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNameCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			System.err.println(kk+"这里才是全链接");
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			System.out.println(sss);
			if(sss==200){
				data = re.get("data");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return data;
	}
	
}
