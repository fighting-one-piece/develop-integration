package org.cisiondata.modules.datainterface.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.cisiondata.modules.datainterface.Constants;
import org.cisiondata.modules.datainterface.entity.SwitchStatus;
import org.cisiondata.modules.datainterface.service.ILMInternetService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("lmInternetService")
public class LMInternetServiceImpl implements ILMInternetService{
	
	/**
	 * 众多接口
	 */
	public String relational_query(String type,String index){
		String params = "searchType="+type+"&searchContent="+index+"";
		String url = "http://csad.devsource.com.cn:11080/HGRJProject/RedirectAction";
		String html = HttpUtils.sendGet(url,params);
		Document doc = Jsoup.parse(html, "UTF-8");
		String elem = doc.getElementsByTag("p").last().text().split("查询结果：")[1];
		return elem;
	}
	//整合11-14接口
	@SuppressWarnings("unchecked")
	public Map<String, Object> readBankPhone(String phone) throws BusinessException {
		Map<String, String> map = new HashMap<String,String>();
		Map<String, Object> data = new HashMap<String,Object>();
		map.put("phone", phone);
		try {
			int status = (int) RedisClusterUtils.getInstance().get("PhoneBank11_14");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String binddata = "";
			String activedata = "";
			String loaddata = "";
			String changeLine = "";
			switch (switchStatus) {
				case DEMO :
					binddata ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130305UfbcCDSEibceumc\",\"data\":{\"result\":{\"debitCardCount\":\"2\",\"creditCardAge\":{\"min\":0,\"max\":0},\"creditCardCount\":\"0\",\"creditCardAging\":{\"min\":0,\"max\":0}},\"flag\":\"1\"}}";
					activedata="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"paymentShortTermVolatilityIndex\":\"0\",\"depositShortTermVolatilityIndex\":\"-6\",\"depositLongTermVolatilityIndex\":\"1\"}}}";
					loaddata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"currentOutstandingLoanCount\":\"1\",\"currentOutstandingLoanAmount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"是\",\"min\":0,\"max\":100,\"unit\":\"元\"}}}}";
					changeLine = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130225pVMUMNPnxAHgDKQ\",\"data\":{\"result\":{\"debitCardPayment3m\":{\"min\":0,\"max\":0},\"debitCardPayment12m\":{\"min\":0,\"max\":0},\"debitCardDeposit3m\":{\"min\":0,\"max\":0},\"debitCardDeposit12m\":{\"min\":0,\"max\":0},\"debitCardRemainingSum\":{\"min\":0,\"unit\":\"元\",\"max\":1000,\"maxIncluded\":\"否\",\"minIncluded\":\"否\"}},\"flag\":\"1\"}}";
					break;
				case TEST:
					binddata = HttpUtils.sendPost(Constants.TEST_URL + "/queryPhoneBankCardBindInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					activedata = HttpUtils.sendPost(Constants.TEST_URL + "/queryActive", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					loaddata = HttpUtils.sendPost(Constants.TEST_URL + "/queryLoadInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					changeLine = HttpUtils.sendPost(Constants.TEST_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					binddata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryPhoneBankCardBindInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					activedata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryActive", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					loaddata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryLoadInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					changeLine = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					 break;
			}
			//存储状态码
			Map<String, Object> mapcode = new HashMap<String,Object>();
			//存储数据
			Map<String, Map<String,Object>> mapresult = new HashMap<String, Map<String,Object>>();
//			//手机号绑定银行卡出入账
//			String changeLine = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			String changeLine = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130225pVMUMNPnxAHgDKQ\",\"data\":{\"result\":{\"debitCardPayment3m\":{\"min\":0,\"max\":0},\"debitCardPayment12m\":{\"min\":0,\"max\":0},\"debitCardDeposit3m\":{\"min\":0,\"max\":0},\"debitCardDeposit12m\":{\"min\":0,\"max\":0},\"debitCardRemainingSum\":{\"min\":0,\"unit\":\"元\",\"max\":1000,\"maxIncluded\":\"否\",\"minIncluded\":\"否\"}},\"flag\":\"1\"}}";
//			//手机号绑定银行卡还款情况
//			String loaddata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryLoadInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			String loaddata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130270yBZlLPormOnlnFP\",\"data\":{\"result\":{},\"flag\":0}}";
//			String loaddata = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"currentOutstandingLoanCount\":\"1\",\"currentOutstandingLoanAmount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"是\",\"min\":0,\"max\":100,\"unit\":\"元\"}}}}";
//			//手机号绑定银行卡账动信息查询
//			String activedata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryActive", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			String activedata="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130285CjRZFlcAyRECwFB\",\"data\":{\"result\":{},\"flag\":\"0\"}}";
//			String activedata="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"flag\":\"1\",\"result\":{\"paymentShortTermVolatilityIndex\":\"0\",\"depositShortTermVolatilityIndex\":\"-6\",\"depositLongTermVolatilityIndex\":\"1\"}}}";
//			//手机号绑定银行卡信息
//			String binddata = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryPhoneBankCardBindInfo", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			String binddata ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117163130305UfbcCDSEibceumc\",\"data\":{\"result\":{\"debitCardCount\":\"2\",\"creditCardAge\":{\"min\":0,\"max\":0},\"creditCardCount\":\"0\",\"creditCardAging\":{\"min\":0,\"max\":0}},\"flag\":\"1\"}}";
			
			Gson gson =new Gson();
			/**
			 * 11
			 */
			mapcode = gson.fromJson(changeLine, Map.class);
			int code=(int)(Double.parseDouble(mapcode.get("errorCode").toString()));
			mapresult = gson.fromJson(changeLine, Map.class);
			int flagcode = (int)(Double.parseDouble(mapresult.get("data").get("flag").toString()));
			if(code == 200){
				if(flagcode == 1){
					data.put("changeLine", mapresult.get("data").get("result"));
				}
				if(flagcode == 0){
					data.put("changeLine", "未查到相关数据");
				}
			}else{
				throw new BusinessException("查询失败");
			}
			/**
			 * 12
			 */
			mapcode = gson.fromJson(loaddata, Map.class);
			int loadcode=(int)(Double.parseDouble(mapcode.get("errorCode").toString()));
			mapresult = gson.fromJson(loaddata, Map.class);
			int loadflag = (int)(Double.parseDouble(mapresult.get("data").get("flag").toString()));
			if(loadcode == 200){
				if(loadflag == 1){
					data.put("loaddata", mapresult.get("data").get("result"));
				}
				if(loadflag == 0){
					data.put("loaddata", "未查到相关信息");
				}
			}else{
				throw new BusinessException("查询失败");
			}
			/**
			 * 13
			 */
			mapcode = gson.fromJson(activedata, Map.class);
			int activecode=(int)(Double.parseDouble(mapcode.get("errorCode").toString()));
			mapresult = gson.fromJson(activedata, Map.class);
			int activeflag = (int)(Double.parseDouble(mapresult.get("data").get("flag").toString()));
			if(activecode == 200){
				if(activeflag == 1){
					data.put("activedata", mapresult.get("data").get("result"));
				}
				if(activeflag == 0){
					data.put("activedata", "未查到相关信息");
				}
			}else{
				throw new BusinessException("查询失败");
			}
			/**
			 * 14
			 */
			mapcode = gson.fromJson(binddata, Map.class);
			int bindcode=(int)(Double.parseDouble(mapcode.get("errorCode").toString()));
			mapresult = gson.fromJson(binddata, Map.class);
			int bindflag = (int)(Double.parseDouble(mapresult.get("data").get("flag").toString()));
			if(bindcode == 200){
				if(bindflag == 1){
					data.put("binddata", mapresult.get("data").get("result"));
				}
				if(bindflag == 0){
					data.put("binddata", "未查到相关信息");
				}
			}else{
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		
		return data;
	}
	//46、反欺诈黑名单验证
		@SuppressWarnings("unchecked")
		@Override
		public Map<String, String> auditphone(String phone)throws BusinessException {
			Map<String, String> data = new HashMap<String, String>();
			Map<String ,String> map=new HashMap<String,String>();
			map.put("phone", phone);
			try {
				int status = (int) RedisClusterUtils.getInstance().get("auditphone");
				SwitchStatus switchStatus = SwitchStatus.getStatus(status);
				String kk = "";
				switch (switchStatus) {
					case DEMO :
						kk = "";
						break;
					case TEST:
						kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/audit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
						break;
					case NORMAL:
						kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/audit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
						break;
				}
//				String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/audit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
				Gson gson =new Gson();
				Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
				Map<String,Object> gg=gson.fromJson(kk,Map.class);
				int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
				if(sss==200){
					data = re.get("data");
				} else {
					throw new BusinessException("查询失败");
				}
			} catch (Exception e) {
				e.getMessage();
				throw new BusinessException("查询失败");
			}
			return data;
		}
		
		/**
		 * 40. 法院被执行人查询（B机构）
		 */
		@SuppressWarnings("unchecked")
		public Map<String, String> court_executedPeopleB(String idCard,String name,String phone) throws BusinessException {
			Map<String, String> map =new HashMap<String, String>();
			Map<String, String> data = new HashMap<String, String>();
			map.put("idCard", idCard);
			map.put("name", name);
			map.put("phone", phone);
			try {
				int status = (int) RedisClusterUtils.getInstance().get("court_executedPeopleB");
				SwitchStatus switchStatus = SwitchStatus.getStatus(status);
				String kk = "";
				switch (switchStatus) {
					case DEMO :
						kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170114143705429CvpACMSNCaPSvhJ\",\"data\":{\"result\":{\"content\":[{\"Execution\":{\"court_bad1\":{\"casenum\":\"(2016)建执字第00047号\",\"breaktime\":\"\",\"datatime\":\"\",\"posttime\":\"2016年05月11日\",\"basecompany\":\"黑龙江省建三江农垦法院\",\"cidtype\":\"\",\"court\":\"黑龙江省建三江农垦法院\",\"unperformpart\":\"\",\"datatypeid\":102,\"cid\":\"2308191981****1510\",\"concretesituation\":\"其他有履行能力而拒不履行生效法律文书确定义务\",\"obligation\":\"一、被告张晓雨、贾兴玲偿还原告李永良欠款210 000元，利息14 580元，合计224 580元，于2015年12月31日履行；二、银行贷款的滞纳金原告按50 000元，被告按210 000元各自承担；案件受理费4192元，减半收取2096元，由原告李永良承担500元，被告张晓雨承担1596元。\",\"time\":\"2016年01月07日\",\"lasttime\":\"\",\"address\":\"黑龙江\",\"performance\":\"全部未履行\",\"name\":\"张晓雨\",\"leader\":\"\",\"performedpart\":\"\",\"money\":\"\",\"base\":\"（2015）建商初字第428号\",\"datatype\":\"失信被执行人\"},\"court_executed2\":{\"statute\":\"已结案\",\"casenum\":\"(2015)建执字第00034号\",\"time\":\"2015-01-19\",\"datatime\":\"2015-01-19\",\"basic\":\"\",\"name\":\"张晓雨\",\"money\":\"26176.00\",\"cidtype\":1,\"court\":\"黑龙江省建三江农垦法院\",\"datatypeid\":103,\"datatype\":\"最高法执行\",\"cid\":\"230819198104171510\",\"basiccourt\":\"\"},\"court_executed1\":{\"statute\":\"\",\"casenum\":\"(2016)建执字第00047号\",\"time\":\"2016-01-07\",\"datatime\":\"\",\"basic\":\"\",\"name\":\"张晓雨\",\"money\":\"244580\",\"cidtype\":\"\",\"court\":\"黑龙江省建三江农垦法院\",\"datatypeid\":103,\"datatype\":\"最高法执行\",\"cid\":\"2308191981****1510\",\"basiccourt\":\"\"}},\"Flag\":{\"execution\":\"1\"}}]}}}";
						break;
					case TEST:
						kk = HttpUtils.sendPost(Constants.TEST_URL + "/court/executedPeopleB", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
						break;
					case NORMAL:
						kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/court/executedPeopleB", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
						break;
				}
//				String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170114143705429CvpACMSNCaPSvhJ\",\"data\":{\"result\":{\"content\":[{\"Execution\":{\"court_bad1\":{\"casenum\":\"(2016)建执字第00047号\",\"breaktime\":\"\",\"datatime\":\"\",\"posttime\":\"2016年05月11日\",\"basecompany\":\"黑龙江省建三江农垦法院\",\"cidtype\":\"\",\"court\":\"黑龙江省建三江农垦法院\",\"unperformpart\":\"\",\"datatypeid\":102,\"cid\":\"2308191981****1510\",\"concretesituation\":\"其他有履行能力而拒不履行生效法律文书确定义务\",\"obligation\":\"一、被告张晓雨、贾兴玲偿还原告李永良欠款210 000元，利息14 580元，合计224 580元，于2015年12月31日履行；二、银行贷款的滞纳金原告按50 000元，被告按210 000元各自承担；案件受理费4192元，减半收取2096元，由原告李永良承担500元，被告张晓雨承担1596元。\",\"time\":\"2016年01月07日\",\"lasttime\":\"\",\"address\":\"黑龙江\",\"performance\":\"全部未履行\",\"name\":\"张晓雨\",\"leader\":\"\",\"performedpart\":\"\",\"money\":\"\",\"base\":\"（2015）建商初字第428号\",\"datatype\":\"失信被执行人\"},\"court_executed2\":{\"statute\":\"已结案\",\"casenum\":\"(2015)建执字第00034号\",\"time\":\"2015-01-19\",\"datatime\":\"2015-01-19\",\"basic\":\"\",\"name\":\"张晓雨\",\"money\":\"26176.00\",\"cidtype\":1,\"court\":\"黑龙江省建三江农垦法院\",\"datatypeid\":103,\"datatype\":\"最高法执行\",\"cid\":\"230819198104171510\",\"basiccourt\":\"\"},\"court_executed1\":{\"statute\":\"\",\"casenum\":\"(2016)建执字第00047号\",\"time\":\"2016-01-07\",\"datatime\":\"\",\"basic\":\"\",\"name\":\"张晓雨\",\"money\":\"244580\",\"cidtype\":\"\",\"court\":\"黑龙江省建三江农垦法院\",\"datatypeid\":103,\"datatype\":\"最高法执行\",\"cid\":\"2308191981****1510\",\"basiccourt\":\"\"}},\"Flag\":{\"execution\":\"1\"}}]}}}";
//				String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/court/executedPeopleB", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
				Gson gson = new Gson();
				Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
				Map<String, Object> gg = gson.fromJson(kk, Map.class);
				int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
				if (sss == 200) {
					 data = re.get("data").get("result");
				} else {
					throw new BusinessException("查询失败");
				}
			}catch(Exception e){
				e.getMessage();
				throw new BusinessException("查询失败");
			}
			return data;
		}
		
		
	/**
	 * 39、学历查询（F机构）
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> education_organizeF(String idCard, String name) throws BusinessException{
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		try {
			int status = (int) RedisClusterUtils.getInstance().get("education_organizeF");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170114113526248VRhZheMBhFkOvUw\",\"data\":{\"message\":\"查询成功无数据\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"startTime\":\"2012\",\"isKeySubject\":\"Y\",\"graduateTime\":\"2014\",\"degree\":\"专科\",\"studyResult\":\"毕业\",\"studyType\":\"成人\",\"studyStyle\":\"业余\",\"college\":\"电子科技大学\",\"photo\":\"/9j/4AAQSkZJRgABAQEBLAEsAAD/2wBDAAIBAQEBAQIBAQECAgICAgQDAgICAgUEBAMEBgUGBgYFBgYGBwkIBgcJBwYGCAsICQoKCgoKBggLDAsKDAkKCgr/2wBDAQICAgICAgUDAwUKBwYHCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgr//gN2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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\",\"specialty\":\"软件技术\",\"levelNo\":\"\",\"photoStyle\":\"\"},\"personBase\":{\"originalAddress\":\"\",\"birthday\":\"99006257\",\"degree\":\"专科\",\"age\":\"\",\"name\":\"何霞\",\"graduateYears\":\"\",\"gender\":\"\",\"documentNo\":\"510322199006257815\"},\"college\":{\"colgCharacter\":\"全日制\",\"colgLevel\":\"博士\",\"postDoctorNum\":\"13\",\"is211\":\"Y\",\"masterDegreeNum\":\"101\",\"colgType\":\"985、211工程院校\",\"scienceBatch\":\"\",\"collegeName\":\"电子科技大学\",\"is985\":\"Y\",\"character\":\"公办\",\"createYears\":\"61\",\"academicianNum\":\"7\",\"manageDept\":\"教育部\",\"address\":\"成都\",\"collegeOldName\":\"\",\"keySubjectNum\":\"6\",\"doctorDegreeNum\":\"66\",\"createDate\":\"1956-01-01\",\"artBatch\":\"\"}}}}";
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170114113526248VRhZheMBhFkOvUw\",\"data\":{\"message\":\"查询成功无数据\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"startTime\":\"2012\",\"isKeySubject\":\"Y\",\"graduateTime\":\"2014\",\"degree\":\"专科\",\"studyResult\":\"毕业\",\"studyType\":\"成人\",\"studyStyle\":\"业余\",\"college\":\"电子科技大学\",\"photo\":\"/9j/4AAQSkZJRgABAQEBLAEsAAD/2wBDAAIBAQEBAQIBAQECAgICAgQDAgICAgUEBAMEBgUGBgYFBgYGBwkIBgcJBwYGCAsICQoKCgoKBggLDAsKDAkKCgr/2wBDAQICAgICAgUDAwUKBwYHCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgr//gN2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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\",\"specialty\":\"软件技术\",\"levelNo\":\"\",\"photoStyle\":\"\"},\"personBase\":{\"originalAddress\":\"\",\"birthday\":\"99006257\",\"degree\":\"专科\",\"age\":\"\",\"name\":\"何霞\",\"graduateYears\":\"\",\"gender\":\"\",\"documentNo\":\"510322199006257815\"},\"college\":{\"colgCharacter\":\"全日制\",\"colgLevel\":\"博士\",\"postDoctorNum\":\"13\",\"is211\":\"Y\",\"masterDegreeNum\":\"101\",\"colgType\":\"985、211工程院校\",\"scienceBatch\":\"\",\"collegeName\":\"电子科技大学\",\"is985\":\"Y\",\"character\":\"公办\",\"createYears\":\"61\",\"academicianNum\":\"7\",\"manageDept\":\"教育部\",\"address\":\"成都\",\"collegeOldName\":\"\",\"keySubjectNum\":\"6\",\"doctorDegreeNum\":\"66\",\"createDate\":\"1956-01-01\",\"artBatch\":\"\"}}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/education/organizeF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/education/organizeF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/education/organizeF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
		
		
	/**
	 * 38、信贷综合信息查询
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, String> readLoanInfo(String phone) throws BusinessException {
		//存储得到的数据
		Map<String, String> mapData = new HashMap<String,String>();
		//接收所传的参数
		Map<String, String> map = new HashMap<String,String>();
		map.put("phone", phone);
		//测试JSON
//		String kk = "{\"errorCode\": 200,\"errorMsg\": \"请求成功\",\"uid\": \"20161205100516170IAWlTRsIPDqNykk\",\"data\": {\"PHONE\": \"18610025754\",\"PROVINCE\": \"北京\",\"RESULTS\": [{\"DATA\": [{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/16 0:00:00\",\"PLATFORMCODE\": \"EM_112197\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/22 0:00:00\",\"PLATFORMCODE\": \"EM_110237\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/25 0:00:00\",\"PLATFORMCODE\": \"EM_103071\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/10 0:00:00\",\"PLATFORMCODE\": \"EM_103071\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/25 0:00:00\",\"PLATFORMCODE\": \"EM_104972\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/5 0:00:00\",\"PLATFORMCODE\": \"EM_110237\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/10/7 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/10/6 0:00:00\",\"PLATFORMCODE\": \"EM_100001 \"}],\"CYCLE\": \"2015-12-05--2016-12-05\",\"TYPE\": \"EMR002\"},{\"DATA\": [{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/21 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/3 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/6 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/5 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"1W～3W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/9/10 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/9/10 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"}],\"CYCLE\": \"2015-12-05--2016-12-05\",\"TYPE\": \"EMR004\"}],\"CITY\": \"北京\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readLoanInfo");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\": 200,\"errorMsg\": \"请求成功\",\"uid\": \"20161205100516170IAWlTRsIPDqNykk\",\"data\": {\"PHONE\": \"18610025754\",\"PROVINCE\": \"北京\",\"RESULTS\": [{\"DATA\": [{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/16 0:00:00\",\"PLATFORMCODE\": \"EM_112197\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/22 0:00:00\",\"PLATFORMCODE\": \"EM_110237\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/25 0:00:00\",\"PLATFORMCODE\": \"EM_103071\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/10 0:00:00\",\"PLATFORMCODE\": \"EM_103071\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/5/25 0:00:00\",\"PLATFORMCODE\": \"EM_104972\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/6/5 0:00:00\",\"PLATFORMCODE\": \"EM_110237\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/10/7 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"REGISTERTIME\": \"2016/10/6 0:00:00\",\"PLATFORMCODE\": \"EM_100001 \"}],\"CYCLE\": \"2015-12-05--2016-12-05\",\"TYPE\": \"EMR002\"},{\"DATA\": [{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/21 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/3 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/6 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/10/5 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"1W～3W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/9/10 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"},{\"P_TYPE\": \"2\",\"APPLICATIONAMOUNT\": \"0W～0.2W\",\"APPLICATIONRESULT\": \" \",\"APPLICATIONTIME\": \"2016/9/10 0:00:00\",\"PLATFORMCODE\": \"EM_100001\"}],\"CYCLE\": \"2015-12-05--2016-12-05\",\"TYPE\": \"EMR004\"}],\"CITY\": \"北京\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/loan/info", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/loan/info", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/loan/info", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
//			Map<String, Map<String,Object>> maps = gson.fromJson(kk,new TypeToken<Map<String,Map<String,Object>>>(){}.getType());
			Map<String, Map<String,String>> ss = gson.fromJson(kk,Map.class);
			//获取状态码
			int code = (int)Double.parseDouble(gson.fromJson(kk,Map.class).get("errorCode").toString());
			if(code == 200){
				mapData = ss.get("data");
			}else{
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return mapData;
	}
	
	/**
	 * 37、学历查询（D机构）
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> education_organizeD(String idCard, String name){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161226141646553JlPiWlDCmjffZDP\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("education_organizeD");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161226141646553JlPiWlDCmjffZDP\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/education/organizeD", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/education/organizeD", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/education/organizeD", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	
	/**
	 * 36、搜索黑名单
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> blacklist_search(String idCard,String name,String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, Map<String, String>> data = new HashMap<String,  Map<String, String>>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161226145554373ApqfHyTjGftiguy\",\"data\":{\"blackLevel\":\"B\",\"blackReason\":\"T03\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("blacklist_search");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161226145554373ApqfHyTjGftiguy\",\"data\":{\"blackLevel\":\"B\",\"blackReason\":\"T03\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/search", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/search", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/search", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 35、数信网黑名单
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213150210248TjNXqhNNDWKARPr\",\"data\":{\"result\":{\"content\":[{\"desc\":\"命中次数 1\",\"originalRet\":{\"app_mobile\":\"\",\"message\":\"网络公开负面信息\",\"home\":\"江西-南昌\",\"hit_num\":1,\"sex\":\"女\",\"app_qq\":\"\",\"hit_list\":[{\"publish_date\":\"2014-12-15\",\"source_url\":\"http://s.digcredit.com/urls/10020/9757709dbc7cb97a47e1c2def585b7da/\",\"province\":\"\",\"court\":\"\",\"file_time\":\"\",\"overdue_amount\":4980.05,\"comp_name\":\"武汉商贸职业学院\"}],\"age\":29,\"xingzuo\":\"天蝎座\",\"app_id_card\":\"36012119861115294X\",\"type\":\"C01\",\"app_name\":\"潘丽\"},\"type\":\"网络公开负面信息\",\"date\":\"\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readBlackList");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213150210248TjNXqhNNDWKARPr\",\"data\":{\"result\":{\"content\":[{\"desc\":\"命中次数 1\",\"originalRet\":{\"app_mobile\":\"\",\"message\":\"网络公开负面信息\",\"home\":\"江西-南昌\",\"hit_num\":1,\"sex\":\"女\",\"app_qq\":\"\",\"hit_list\":[{\"publish_date\":\"2014-12-15\",\"source_url\":\"http://s.digcredit.com/urls/10020/9757709dbc7cb97a47e1c2def585b7da/\",\"province\":\"\",\"court\":\"\",\"file_time\":\"\",\"overdue_amount\":4980.05,\"comp_name\":\"武汉商贸职业学院\"}],\"age\":29,\"xingzuo\":\"天蝎座\",\"app_id_card\":\"36012119861115294X\",\"type\":\"C01\",\"app_name\":\"潘丽\"},\"type\":\"网络公开负面信息\",\"date\":\"\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
			//调用数信网黑名单API
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	
	/**
	 * 34、多次申请记录查询C
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readMultiPlatform(String phone) {
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213164108481HkYTOaePyhlATJm\",\"data\":{\"result\":{\"content\":[{\"desc\":\"【图】- 工行逸贷,pos机招商,办理- 邯郸邯郸担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 个人无抵押,无担保小额贷款,1-3天放款! - 邯郸邯郸担保/贷款...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"xiao13403307888_借款列表_个人主页_个人小额短期无抵押贷款_...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邢台邢台担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邯郸邯郸担保/贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 安阳安阳新区担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"...xiao13403307888的借款_个人小额短期无抵押贷款_P2P网..._拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【累积信用】xiao13403307888的首次借款—wap专享_ ... - 拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【网贷体验】听说网上能贷款，体验下网贷_xiao13403307888的借款_ ...\",\"type\":\"贷款相关\",\"date\":\"\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readMultiPlatform");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213164108481HkYTOaePyhlATJm\",\"data\":{\"result\":{\"content\":[{\"desc\":\"【图】- 工行逸贷,pos机招商,办理- 邯郸邯郸担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 个人无抵押,无担保小额贷款,1-3天放款! - 邯郸邯郸担保/贷款...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"xiao13403307888_借款列表_个人主页_个人小额短期无抵押贷款_...\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邢台邢台担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 邯郸邯郸担保/贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【图】- 工行逸贷建行E贷- 安阳安阳新区担保贷款- 百姓网\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"...xiao13403307888的借款_个人小额短期无抵押贷款_P2P网..._拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【累积信用】xiao13403307888的首次借款—wap专享_ ... - 拍拍贷\",\"type\":\"贷款相关\",\"date\":\"\"},{\"desc\":\"【网贷体验】听说网上能贷款，体验下网贷_xiao13403307888的借款_ ...\",\"type\":\"贷款相关\",\"date\":\"\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/loan/multiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/loan/multiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
			//调用多次申请记录查询API
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/loan/multiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213174041396mDJfxNsgcXXSGTt\",\"data\":{\"result\":{\"content\":[{\"desc\":\"王龙\",\"originalRet\":{\"black_del_max_days\":\"43\",\"court_name\":\"\",\"black_source\":\"拍拍贷\",\"black_user_id\":\"\",\"disrupt_type_name\":\"\",\"crawl_time\":\"2015-08-06\",\"unperform_part\":\"\",\"black_home_addr\":\"\",\"black_user_mobile\":\"13986679***\",\"id\":\"118077\",\"mobile_equipment_number\":\"\",\"level\":\"1\",\"black_company_addr\":\"\",\"black_user_name\":\"王龙\",\"duty\":\"\",\"performance\":\"\",\"user_case_code\":\"\",\"qq\":null,\"black_user_gender\":\"0\",\"black_update_time\":\"0000-00-00\",\"publish_date\":\"\",\"user_type\":\"\",\"performed_part\":\"\",\"black_overdue_amt\":\"0.00\",\"black_user_phone\":\"\",\"black_del_cnt\":\"0\",\"black_company_name\":\"\",\"black_loan_amt\":\"0.00\",\"source_mobile\":\"13986679000\",\"black_email\":\"\"},\"date\":\"0000-00-00\",\"type\":\"拍拍贷\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readLoanOverdue");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213174041396mDJfxNsgcXXSGTt\",\"data\":{\"result\":{\"content\":[{\"desc\":\"王龙\",\"originalRet\":{\"black_del_max_days\":\"43\",\"court_name\":\"\",\"black_source\":\"拍拍贷\",\"black_user_id\":\"\",\"disrupt_type_name\":\"\",\"crawl_time\":\"2015-08-06\",\"unperform_part\":\"\",\"black_home_addr\":\"\",\"black_user_mobile\":\"13986679***\",\"id\":\"118077\",\"mobile_equipment_number\":\"\",\"level\":\"1\",\"black_company_addr\":\"\",\"black_user_name\":\"王龙\",\"duty\":\"\",\"performance\":\"\",\"user_case_code\":\"\",\"qq\":null,\"black_user_gender\":\"0\",\"black_update_time\":\"0000-00-00\",\"publish_date\":\"\",\"user_type\":\"\",\"performed_part\":\"\",\"black_overdue_amt\":\"0.00\",\"black_user_phone\":\"\",\"black_del_cnt\":\"0\",\"black_company_name\":\"\",\"black_loan_amt\":\"0.00\",\"source_mobile\":\"13986679000\",\"black_email\":\"\"},\"date\":\"0000-00-00\",\"type\":\"拍拍贷\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanOverdue", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanOverdue", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
			//调用网络公开逾期信息API
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanOverdue", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213181030680ffGuAlglZbXRqIC\",\"data\":{\"result\":{\"content\":[{\"desc\":\"欺诈申请\",\"originalRet\":{\"loan_type\":\"\",\"name\":\"100\",\"confirm_type\":\"欺诈\",\"pid\":\"100\",\"applied_at\":\"\",\"confirm_details\":\"欺诈申请\",\"mobile\":\"0\",\"confirmed_at\":\"2012年08月01日\"},\"type\":\"欺诈\",\"date\":\"\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readBlacklistCheat");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					 kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213181030680ffGuAlglZbXRqIC\",\"data\":{\"result\":{\"content\":[{\"desc\":\"欺诈申请\",\"originalRet\":{\"loan_type\":\"\",\"name\":\"100\",\"confirm_type\":\"欺诈\",\"pid\":\"100\",\"applied_at\":\"\",\"confirm_details\":\"欺诈申请\",\"mobile\":\"0\",\"confirmed_at\":\"2012年08月01日\"},\"type\":\"欺诈\",\"date\":\"\"}]}}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/cheat", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/cheat", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/cheat", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216205655422RXmkXYUMaXWRUqL\",\"data\":{\"result\":{\"content\":[{\"desc\":\"<a href=\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\" style=\"color:#E3AD1C\"  target=\"_blank\">【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网</a>\",\"originalRet\":{\"abstract\":\"赖永超 18950848338 18950848338福建 35050019901020*闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573山西 14270319890323*西安高新科技职业学...\",\"title\":\"【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网\",\"no\":1,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\"},\"type\":\"黑名单\",\"date\":\"\"},{\"desc\":\"<a href=\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\" style=\"color:#E3AD1C\"  target=\"_blank\">贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼</a>\",\"originalRet\":{\"abstract\":\"2014年11月17日 - 赖永超 18950848338 18950848338 福建 350500199010205535 闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573 山西 142703198903232416 ...\",\"title\":\"贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼\",\"no\":11,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\"},\"type\":\"黑名单\",\"date\":\"\"}]}}}";
//		{"errorCode":200,"errorMsg":"请求成功","uid":"20161216205655422RXmkXYUMaXWRUqL","data":{"result":{"content":[{"desc":"<a href=\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\" style=\"color:#E3AD1C\"  target=\"_blank\">【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网</a>","originalRet":{"abstract":"赖永超 18950848338 18950848338福建 35050019901020*闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573山西 14270319890323*西安高新科技职业学...","title":"【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网","no":1,"class":"黑名单","search_word":"18950848338","search_type":"phone","url":"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou"},"type":"黑名单","date":""},{"desc":"<a href=\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\" style=\"color:#E3AD1C\"  target=\"_blank\">贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼</a>","originalRet":{"abstract":"2014年11月17日 - 赖永超 18950848338 18950848338 福建 350500199010205535 闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573 山西 142703198903232416 ...","title":"贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼","no":11,"class":"黑名单","search_word":"18950848338","search_type":"phone","url":"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq"},"type":"黑名单","date":""}]}}}
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readInternetNegative");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216205655422RXmkXYUMaXWRUqL\",\"data\":{\"result\":{\"content\":[{\"desc\":\"<a href=\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\" style=\"color:#E3AD1C\"  target=\"_blank\">【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网</a>\",\"originalRet\":{\"abstract\":\"赖永超 18950848338 18950848338福建 35050019901020*闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573山西 14270319890323*西安高新科技职业学...\",\"title\":\"【P2PBLACK】出大事啦,那些老赖可怎么办! _网贷预警网\",\"no\":1,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.so.com/link?url=http%3A%2F%2Fwww.wangdaiyujing.com%2Fp2p-61919-1.html&q=18950848338&ts=1471514870&t=9f79f5217615ffde0e6cbf1e87e8aae&src=haosou\"},\"type\":\"黑名单\",\"date\":\"\"},{\"desc\":\"<a href=\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\" style=\"color:#E3AD1C\"  target=\"_blank\">贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼</a>\",\"originalRet\":{\"abstract\":\"2014年11月17日 - 赖永超 18950848338 18950848338 福建 350500199010205535 闽西职业技术学院 3000 2334.23 杨国良 13133396573 13133396573 山西 142703198903232416 ...\",\"title\":\"贝才网借贷逾期黑名单 望各大平台注意老赖-天眼数据-网贷天眼\",\"no\":11,\"class\":\"黑名单\",\"search_word\":\"18950848338\",\"search_type\":\"phone\",\"url\":\"http://www.baidu.com/link?url=HRWibRes9n6suQPw0Xg91wVhXteTlFqQpVpd4QmZnrEFGWZWzQMl8VwYDzCn6DGpgsC0j3EAHEElK5x5RSEyQq\"},\"type\":\"黑名单\",\"date\":\"\"}]}}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			kk = kk.replaceAll("<a href=\\\"http", "<a href=\\'http").replaceAll("\\\" style=\\\"", "\\' style=\\'");
			kk = kk.replaceAll("\\\"  target=\\\"_blank\\\"", "\\'  target=\\'_blank\\'");
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
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
//	    String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216112015658ZgRXWYjtmVRgpda\",\"data\":{\"result\":{\"content\":[{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201503期方案:100437150 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150202期方案:99727487 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150301期方案:100430851 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:97727442 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97595811 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:99712532 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150301期方案:100489788 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15015期方案:97557409 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97689257 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15031期方案:100284165 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150202期方案:99520485 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97751148 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150301期方案:100489785 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球混合过关201502期方案:97572774 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150103期方案:97029786 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98433767 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98405400 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:97530337 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:98185464 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"14场胜负彩2015018期方案：97507488 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("blacklistGamble");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161216112015658ZgRXWYjtmVRgpda\",\"data\":{\"result\":{\"content\":[{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201503期方案:100437150 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150202期方案:99727487 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150301期方案:100430851 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"13524384545的个人空间_彩客网\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:97727442 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97595811 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:99712532 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150301期方案:100489788 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15015期方案:97557409 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97689257 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"超级大乐透15031期方案:100284165 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩混合过关20150202期方案:99520485 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球胜负201502期方案:97751148 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150301期方案:100489785 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"篮球混合过关201502期方案:97572774 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150103期方案:97029786 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98433767 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩胜平负20150201期方案:98405400 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:97530337 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"竞彩让球胜平负20150201期方案:98185464 -- 彩客 \",\"type\":\"博彩\",\"date\":\"\"},{\"desc\":\"14场胜负彩2015018期方案：97507488 -- 彩客\",\"type\":\"博彩\",\"date\":\"\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/gamble", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/gamble", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/gamble", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson=new Gson();
			Map<String, Map<String, Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	
	
	/**
	 * 29多次申请记录查询B 数据为空
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> repeatedlyInquireB(String idCard,String name,String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220144236902PoRmGyqrosKGpeC\",\"data\":{\"result\":{\"content\":[{}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("repeatedlyInquireB");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220144236902PoRmGyqrosKGpeC\",\"data\":{\"result\":{\"content\":[{}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/multiApplyCheckBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/multiApplyCheckBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/multiApplyCheckBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 28多次申请记录查询A  接口有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> repeatedlyInquireA(String idCard,String name,String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
//		String kk ="";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("repeatedlyInquireA");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/debtMultiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/debtMultiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/debtMultiPlatform", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 27 银行p2p预期记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> bankP2P(String idCard,String name,String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170110115639265AZdQkDpLuPGvuPb\",\"data\":{\"result\":{\"content\":[{\"Flag\":{\"specialList_c\":\"1\"},\"SpecialList_c\":{\"id\":{\"abnormal\":\"0\"},\"cell\":{},\"gid\":{},\"lm_cell\":{}}}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("bankP2P");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170110115639265AZdQkDpLuPGvuPb\",\"data\":{\"result\":{\"content\":[{\"Flag\":{\"specialList_c\":\"1\"},\"SpecialList_c\":{\"id\":{\"abnormal\":\"0\"},\"cell\":{},\"gid\":{},\"lm_cell\":{}}}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/socialContact", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/socialContact", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/socialContact", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 26 法院被执行人记录
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> CourtExecutePeople(String idCard,String name,String phone,String idType,String gender){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("phone", phone);
		map.put("idType", idType);
		map.put("gender", gender);
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220162850089OIxXlqVjGgiyKpJ\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"id_type\":1,\"baidu_account\":\"\",\"discredit_records\":{\"netloan\":null,\"tax\":null,\"court\":null},\"real_name\":\"吴燕\",\"imei\":\"\",\"gender\":1,\"id_no\":\"610628197707020043\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("CourtExecutePeople");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220162850089OIxXlqVjGgiyKpJ\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"id_type\":1,\"baidu_account\":\"\",\"discredit_records\":{\"netloan\":null,\"tax\":null,\"court\":null},\"real_name\":\"吴燕\",\"imei\":\"\",\"gender\":1,\"id_no\":\"610628197707020043\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklist/loanCredit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanCredit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklist/loanCredit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 25 手机号标签查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> phoneTagQuery(String phone){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220165426378ODcpeCEOyYHRwrh\",\"data\":{\"result\":{\"content\":[{\"tags\":[{\"count\":\"9\",\"tag_name\":\"济仁诊所\"}],\"phone\":\"13899787289\",\"com_address\":null,\"carrier\":\"新疆伊犁移动\",\"com_name\":null}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("phoneTagQuery");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220165426378ODcpeCEOyYHRwrh\",\"data\":{\"result\":{\"content\":[{\"tags\":[{\"count\":\"9\",\"tag_name\":\"济仁诊所\"}],\"phone\":\"13899787289\",\"com_address\":null,\"carrier\":\"新疆伊犁移动\",\"com_name\":null}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/phone/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/phone/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/phone/label", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 24地址验证
	 */ 
	@SuppressWarnings("unchecked")
	public Map<String, String> addresVerification(String idCard,String name,String phone,String idType,String homeCity,String homeAddress,String companyCity,String companyAddress){
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
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220172036886lEqAWUKXHwZQOtW\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"local_freq_address_list\":null,\"id_type\":1,\"baidu_account\":\"\",\"real_name\":\"吴燕\",\"imei\":\"\",\"company_address_list\":[{\"distance\":\"1252862.0\",\"verify_result\":\"D\"}],\"home_address_list\":null,\"nolocal_freq_address_list\":null,\"id_no\":\"610628197707020043\"}]}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("addresVerification");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220172036886lEqAWUKXHwZQOtW\",\"data\":{\"result\":{\"content\":[{\"cell_phone\":\"18691179115\",\"local_freq_address_list\":null,\"id_type\":1,\"baidu_account\":\"\",\"real_name\":\"吴燕\",\"imei\":\"\",\"company_address_list\":[{\"distance\":\"1252862.0\",\"verify_result\":\"D\"}],\"home_address_list\":null,\"nolocal_freq_address_list\":null,\"id_no\":\"610628197707020043\"}]}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/address/verify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/address/verify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/address/verify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 23 百度消费金融评分查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> baiduQuery(String phone,String name,String idCard,String maritalStatus,String degree,String homeAddress,String companyAddress){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("maritalStatus", maritalStatus);
		map.put("degree", degree);
		map.put("homeAddress", homeAddress);
		map.put("companyAddress", companyAddress);
//		String kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220175529058pfzZttkXoltdKpm\",\"data\":{\"lemon_A_pd\":\"0.1721499860\",\"lemon_B_pd\":\"0.0820594504\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("baiduQuery");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161220175529058pfzZttkXoltdKpm\",\"data\":{\"lemon_A_pd\":\"0.1721499860\",\"lemon_B_pd\":\"0.0820594504\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/credit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/credit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/credit", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 22申请数据查询     接口有问题
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readappKeys(String phone,String idCard,String name,String bankCard){
		Map<String, String> map =new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
//		String kk ="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readappKeys");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk ="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/applyDataInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/applyDataInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/applyDataInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data").get("message");
			} else {
				throw new BusinessException("查询失败");
			}
		}catch(Exception e){
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215202241570BwyLCmksqPGifZv\",\"data\":{\"hits\":\"0\",\"message\":\"未命中\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readCardUti2");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215202241570BwyLCmksqPGifZv\",\"data\":{\"hits\":\"0\",\"message\":\"未命中\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/blacklistInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklistInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/blacklistInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson=new Gson();
			Map<String,Map<String,String>> re = gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
			
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
			
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215150250391mzwEJyTakXyQUpa\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readCardUtil");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161215150250391mzwEJyTakXyQUpa\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/staffReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/staffReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/staffReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161222165911932BAphTgQdwSHLIsV\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readCaseReportInfo");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功!\",\"uid\":\"20161222165911932BAphTgQdwSHLIsV\",\"data\":{\"hits\":\"1\",\"message\":\"命中\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/caseReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/caseReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/caseReportInfoQuery", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 18、 银行卡消费信息查询1
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>>  readqueryQuota1(String bankCard, String idCard, String phone, String name){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		map.put("name", name);
		map.put("index", "S0659,S0477,S0474,S0660,S0661,S0464,S0467,S0462,S0465,S0466,S0468,S0469,S0517,S0677,S0618,S0622,S0623,S0624,S0625,S0670,S0671,S0210,S0211,S0213,S0214,S0049,S0050,S0043,S0044,S0132,S0133,S0657,S0658,S0674,S0493,S0662,S0491,S0470,S0663,S0516,S0471,S0472,S0664,S0652,S0473,S0665,S0653,S0654,S0655,S0492,S0494,S0475,S0476,S0480,S0481,S0503,S0504,S0483,S0656,S0518,S0519,S0520,S0521,S0522,S0523,S0524,S0525,S0526,S0527,S0528,S0529,S0530,S0531,S0487,S0488,S0489,S0490,S0605,S0606,S0607,S0608,S0609,S0610,S0611,S0612,S0613,S0614,S0532,S0533,S0499,S0500,S0145,S0146,S0147,S0148,S0149,S0150,S0495");
		try {
			int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					 kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 18、 银行卡消费信息查询2
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>>  readqueryQuota2(String bankCard, String idCard, String phone, String name){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		map.put("name", name);
		map.put("index", "S0505,S0506,S0135,S0136,S0134,S0138,S0507,S0508,S0509,S0510,S0511,S0512,S0513,S0534,S0535,S0536,S0560,S0549,S0550,S0075,S0551,S0128,S0563,S0544,S0567,S0547,S0548,S0568,S0569,S0570,S0537,S0538,S0539,S0561,S0552,S0553,S0078,S0554,S0131,S0564,S0053,S0054,S0541,S0542,S0543,S0566,S0540,S0562,S0545,S0546,S0565,S0573,S0574,S0575,S0576,S0083,S0577,S0086,S0578,S0579,S0084,S0580,S0087,S0581,S0582,S0583,S0584,S0585,S0300,S0301,S0302,S0303,S0304,S0305,S0120,S0121,S0122,S0127,S0123,S0124,S0125,S0130,S0082,S0085,S0108,S0109,S0111,S0112,S0113,S0297,S0298,S0299");
		try {
			int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					 kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 18、 银行卡消费信息查询3
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>>  readqueryQuota3(String bankCard, String idCard, String phone, String name){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		map.put("name", name);
		map.put("index", "S0683,S0094,S0095,S0096,S0091,S0092,S0093,S0099,S0106,S0102,S0107,S0680,S0681,S0056,S0057,S0058,S0059,S0060,S0682,S0348,S0349,S0350,S0351,S0352,S0353,S0052,S0586,S0587,S0588,S0589,S0590,S0591,S0592,S0593,S0594,S0595,S0596,S0597,S0598,S0599,S0600,S0332,S0335,S0334,S0331,S0684,S0685,S0686,S0687,S0629,S0630,S0631,S0632,S0372,S0373,S0374,S0633,S0634,S0635,S0282,S0283,S0284,S0279,S0280,S0281,S0336,S0337,S0338,S0339,S0340,S0341,S0342,S0343,S0344,S0345,S0346,S0347,S0307,S0308,S0313,S0314,S0316,S0317,S0319,S0320,S0555,S0556,S0557,S0558,S0515,S0559,S0615,S0616,S0110,S0617,S0018,S0021,S0024,S0019,S0020,S0022,S0023");
		try {
			int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					 kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 18、 银行卡消费信息查询4
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>>  readqueryQuota4(String bankCard, String idCard, String phone, String name){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		map.put("name", name);
		map.put("index", "S0378,S0379,S0381,S0382,S0166,S0167,S0384,S0385,S0386,S0387,S0388,S0389,S0151,S0152,S0153,S0160,S0161,S0162,S0163,S0164,S0165,S0572,S0668,S0666,S0647,S0649,S0669,S0667,S0645,S0650,S0636,S0637,S0640,S0641,S0642,S0643,S0648,S0646,S0174,S0175,S0025,S0026,S0027,S0031,S0032,S0033,S0037,S0038,S0039,S0028,S0029,S0030,S0034,S0035,S0036,S0040,S0041,S0042,S0433,S0434,S0436,S0437,S0432,S0435,S0438,S0441,S0444,S0447,S0204,S0205,S0207,S0208,S0456,S0459,S0439,S0440,S0442,S0443,S0451,S0452,S0454,S0455,S0457,S0458,S0460,S0461,S0445,S0446,S0448,S0449,S0672,S0673,S0626,S0627,S0628,S0620,S0621,S0276,S0277,S0278,S0294,S0295,S0296,S0291,S0292,S0293,S0571,S0051,S0013,S0014,S0015,S0016,S0601,S0602,S0603,S0604,S0514,S0114,S0115,S0116,S0117,S0118,S0119");
		try {
			int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					 kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 18、 银行卡消费信息查询5
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>>  readqueryQuota5(String bankCard, String idCard, String phone, String name){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("bankCard", bankCard);
		map.put("idCard", idCard);
		map.put("phone", phone);
		map.put("name", name);
		map.put("index", "S0678,S0679,S0644,S0651,S0675,S0676,S0253,S0254,S0256,S0257,S0259,S0260,S0262,S0263,S0265,S0266,S0268,S0269,S0241,S0242,S0244,S0245,S0235,S0236,S0238,S0239,S0222,S0223,S0224,S0226,S0227,S0229,S0230,S0232,S0233,S0199,S0200,S0193,S0194,S0196,S0197,S0187,S0188,S0190,S0191,S0181,S0182,S0184,S0185,S0391,S0392,S0394,S0395,S0397,S0398,S0400,S0401,S0003,S0252,S0255,S0258,S0261,S0264,S0267,S0240,S0243,S0234,S0237,S0225,S0228,S0231,S0198,S0201,S0202,S0203,S0192,S0195,S0186,S0189,S0180,S0183,S0390,S0393,S0396,S0399,S0402,S0405,S0408,S0411,S0403,S0404,S0406,S0407,S0409,S0410,S0412,S0413,S0415,S0416,S0418,S0419,S0421,S0422,S0424,S0425,S0414,S0417,S0420,S0423,S0427,S0428,S0430,S0431,S0426,S0429,S0216,S0217,S0218,S0219,S0220,S0221,S0450,S0453,S0246,S0247,S0248,S0249,S0250,S0251");
		try {
			int status = (int) RedisClusterUtils.getInstance().get("BankQueryQuota");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20170117142917946vthcScvMzUIGmHU\",\"data\":{\"statCode\":\"1000\",\"statMsg\":\"查询成功\",\"validate\":\"1\",\"result\":{\"quota\":{\"S0520\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0521\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_800.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0528\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0529\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0526\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0527\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0524\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0525\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0522\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0523\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_1;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0656\":\"29%\",\"S0579\":\"2900_1;1200_1;1000_1;1460_3\",\"S0578\":\"2900_1;1200_1;1000_1;1460_4\",\"S0577\":\"2900_0.00;1200_800.00;1000_1000.00;1460_3600.00\",\"S0519\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_1;12_1;13_NA;14_NA;15_NA;16_NA;17_1;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0474\":\"11370.00\",\"S0570\":\"2400.00\",\"S0128\":\"90%\",\"S0573\":\"三线城市\",\"S0530\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_河北永通电子科技有限公司;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0531\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_NA;1606_廊坊市中医医院;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0537\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_1;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0078\":\"3\",\"S0538\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0539\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0075\":\"810.00\",\"S0534\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0535\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2990.00;1606_1600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0536\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0567\":\"80%\",\"S0566\":\"75%\",\"S0131\":\"100%\",\"S0569\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_3;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0568\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_2400.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0467\":\"未知\",\"S0136\":\"2900\",\"S0468\":\"金穗通宝卡(银联卡)\",\"S0135\":\"20160728\",\"S0469\":\"农业银行\",\"S0134\":\"0.00\",\"S0464\":\"借记卡\",\"S0561\":\"100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%;100%\",\"S0465\":\"62银联标准卡\",\"S0138\":\"银联智慧信息服务（上海）有限公司\",\"S0560\":\"100%;100%;100%;100%;100%;60%;100%;100%;100%;100%;100%;100%\",\"S0466\":\"未知\",\"S0563\":\"90%\",\"S0562\":\"100%;100%;100%;100%;25%;60%;100%;100%;100%;100%;100%;100%\",\"S0565\":\"90%\",\"S0462\":\"人民币卡\",\"S0564\":\"100%\",\"S0660\":\"未知\",\"S0661\":\"否\",\"S0087\":\"1200_1;1460_2\",\"S0503\":\"NA;NA;NA;NA;NA;廊坊市\",\"S0086\":\"1200_1;1460_1\",\"S0506\":\"20160415\",\"S0507\":\"20160728\",\"S0504\":\"廊坊市\",\"S0505\":\"3\",\"S0083\":\"1200_800.00;1460_0.00\",\"S0084\":\"1200_800.00;1460_0.00\",\"S0554\":\"3\",\"S0553\":\"2\",\"S0552\":\"0\",\"S0551\":\"810.00\",\"S0550\":\"800.00\",\"S0053\":\"1611_0.00,0.00,0.00,0.00,0.00,0.00;1610_0.00,0.00,0.00,0.00,0.00,0.00;1609_0.00,0.00,0.00,0.00,0.00,0.00;1608_0.00,0.00,0.00,0.00,0.00,0.00;1607_0.00,0.00,0.00,0.00,0.00,0.00;1606_0.00,0.00,0.00,800.00,0.00,0.00\",\"S0054\":\"1611_0,0,0,0,0,0;1610_0,0,0,0,0,0;1609_0,0,0,0,0,0;1608_0,0,0,0,0,0;1607_0,0,0,1,0,0;1606_0,0,0,1,0,0\",\"S0510\":\"银联智慧信息服务（上海）有限公司\",\"S0511\":\"810.00\",\"S0512\":\"3\",\"S0513\":\"7\",\"S0517\":\"未知\",\"S0518\":\"01_NA;02_NA;03_NA;04_NA;05_NA;06_NA;07_NA;08_NA;09_NA;10_NA;11_0.00;12_0.00;13_NA;14_NA;15_NA;16_NA;17_800.00;18_NA;19_NA;20_NA;21_NA;22_NA;23_NA;24_NA\",\"S0509\":\"0.00\",\"S0549\":\"0.00\",\"S0584\":\"1460;2900;1200\",\"S0508\":\"2900\",\"S0548\":\"1607;1604\",\"S0585\":\"1460;1200;2900\",\"S0545\":\"3\",\"S0580\":\"2900_0.00;1460_3600.00;1000_1000.00;1200_800.00\",\"S0544\":\"270.00\",\"S0581\":\"2900_1;1460_5;1000_1;1200_1\",\"S0547\":\"2\",\"S0582\":\"2900_1;1460_4;1000_1;1200_1\",\"S0546\":\"1607;1606;1604\",\"S0541\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0540\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_4;1606_2;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0483\":\"1460\",\"S0543\":\"1607;1604\",\"S0542\":\"270.00\",\"S0024\":\"01_50%;03_38%;07_12%\",\"S0021\":\"01;03;07\",\"S0277\":\"2\",\"S0276\":\"2\",\"S0279\":\"0.00\",\"S0278\":\"2\",\"S0572\":\"6011;8062;4814;6012\",\"S0571\":\"1611:NA;1610:NA;1609:NA;1608:NA;1607:2000.00_1460_20160704_6011_31253001廊坊市管道局新十区（银河大街129,0.00_1460_20160716_4814_河北永通电子科技有限公司,0.00_2900_20160728_6012_银联智慧信息服务（上海）有限公司,1000.00_1000_20160714_6011_中国建设银行;1606:800.00_1200_20160616_8062_廊坊市中医医院,1000.00_1460_20160622_6011_08378280廊坊市银河北路129号,600.00_1460_20160622_6011_31253001廊坊市管道局新十区（银河大街129;1605:NA;1604:0.00_1460_20160415_4814_河北永通电子科技有限公司;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0616\":\"S22;S24;S31\",\"S0617\":\"61\",\"S0615\":\"3\",\"S0646\":\"6011;8062;4814;6012\",\"S0648\":\"4\",\"S0600\":\"0%\",\"S0590\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000.00;1606_1800.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0629\":\"85%\",\"S0597\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0598\":\"0%\",\"S0595\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0596\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0559\":\"7;7.7%\",\"S0593\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0558\":\"149%\",\"S0635\":\"88%;NA\",\"S0634\":\"1607_2;1606_2;NA\",\"S0557\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_100.00%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0594\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0633\":\"1607_3000.00;1606_1600.00;NA\",\"S0556\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_33.33%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0591\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_2;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0632\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1000,1460;1606_1460;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0555\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_24.58%;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA\",\"S0592\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2000.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0631\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_2;1606_2;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0630\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_3000.00;1606_1600.00;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0599\":\"100%\",\"S0670\":\"0.00\",\"S0671\":\"0\",\"S0515\":\"75%\",\"S0281\":\"2000.00\",\"S0618\":\"1\",\"S0282\":\"0\",\"S0280\":\"2000.00\",\"S0626\":\"41\",\"S0018\":\"3\",\"S0625\":\"0\",\"S0284\":\"2\",\"S0628\":\"41\",\"S0586\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_0.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0627\":\"20161104\",\"S0283\":\"2\",\"S0587\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_1;1606_0;1605_NA;1604_1;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0622\":\"1\",\"S0110\":\"12\",\"S0621\":\"1611_3;1610_NA;1609_NA;1608_NA;1607_NA;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0624\":\"0\",\"S0623\":\"0\",\"S0620\":\"1611:41_3;1610:NA;1609:NA;1608:NA;1607:NA;1606:NA;1605:NA;1604:NA;1603:NA;1602:NA;1601:NA;1512:NA\",\"S0588\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0.00;1606_600.00;1605_NA;1604_0.00;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0589\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_0;1606_1;1605_NA;1604_0;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0609\":\"廊坊市中医医院\",\"S0608\":\"河北永通电子科技有限公司\",\"S0607\":\"廊坊市中医医院\",\"S0238\":\"0\",\"S0682\":\"07_0.00_1;NA\",\"S0239\":\"0\",\"S0236\":\"0.00\",\"S0235\":\"0.00\",\"S0232\":\"0\",\"S0233\":\"0\",\"S0230\":\"0.00\",\"S0612\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0613\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0610\":\"河北永通电子科技有限公司\",\"S0611\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0614\":\"河北永通电子科技有限公司;廊坊市中医医院;NA;NA;NA\",\"S0651\":\"1\",\"S0650\":\"6011;4814;6012;8062;NA\",\"S0229\":\"0.00\",\"S0226\":\"0\",\"S0227\":\"0\",\"S0222\":\"0.00\",\"S0260\":\"0.00\",\"S0223\":\"0.00\",\"S0224\":\"0.00\",\"S0268\":\"0\",\"S0266\":\"0.00\",\"S0645\":\"6011;8062;4814;6012;NA\",\"S0265\":\"0.00\",\"S0647\":\"6011_4600.00;8062_800.00;4814_0.00;6012_0.00\",\"S0263\":\"0\",\"S0262\":\"0\",\"S0649\":\"6011_4;4814_2;6012_1;8062_1\",\"S0603\":\"2000.00;S24\",\"S0604\":\"2000.00;S24\",\"S0269\":\"0\",\"S0188\":\"0.00\",\"S0259\":\"0.00\",\"S0187\":\"0.00\",\"S0185\":\"0\",\"S0148\":\"0_0%\",\"S0184\":\"0\",\"S0149\":\"河北永通电子科技有限公司_50%;廊坊市中医医院_50%\",\"S0667\":\"25;29;14;19;17\",\"S0146\":\"廊坊市中医医院_100%\",\"S0668\":\"11_0.00;12_0.00;13_0.00;14_0.00;15_0.00;16_0.00;17_0.00;18_0.00;19_0.00;20_0.00;21_0.00;22_0.00;23_0.00;24_0.00;25_4600.00;26_0.00;27_0.00;28_0.00;29_800.00;30_0.00;31_0.00;32_0.00\",\"S0147\":\"廊坊市中医医院_100%\",\"S0253\":\"0.00\",\"S0145\":\"0_0%\",\"S0666\":\"11_0;12_0;13_0;14_2;15_0;16_0;17_0;18_0;19_0;20_0;21_0;22_0;23_0;24_0;25_4599;26_0;27_0;28_0;29_807;30_0;31_0;32_0\",\"S0256\":\"0\",\"S0257\":\"0\",\"S0254\":\"0.00\",\"S0669\":\"25;29;14;17;22\",\"S0182\":\"0.00\",\"S0181\":\"0.00\",\"S0199\":\"800.00\",\"S0200\":\"800.00\",\"S0196\":\"0\",\"S0672\":\"0.00\",\"S0673\":\"2\",\"S0197\":\"0\",\"S0241\":\"0.00\",\"S0678\":\"0.00\",\"S0242\":\"0.00\",\"S0514\":\"2000.00;S24\",\"S0679\":\"0.00\",\"S0244\":\"0\",\"S0245\":\"0\",\"S0114\":\"0.00\",\"S0150\":\"河北永通电子科技有限公司_67%;廊坊市中医医院_33%\",\"S0191\":\"0\",\"S0446\":\"0.00\",\"S0445\":\"0.00\",\"S0193\":\"0.00\",\"S0448\":\"0\",\"S0119\":\"1\",\"S0194\":\"0.00\",\"S0118\":\"1\",\"S0117\":\"0\",\"S0449\":\"0\",\"S0116\":\"800.00\",\"S0115\":\"800.00\",\"S0190\":\"0\",\"S0433\":\"0.00\",\"S0432\":\"0.00\",\"S0121\":\"75%\",\"S0431\":\"0\",\"S0120\":\"100%\",\"S0430\":\"0\",\"S0123\":\"100%\",\"S0122\":\"85%\",\"S0125\":\"100%\",\"S0124\":\"100%\",\"S0476\":\"0\",\"S0127\":\"80%\",\"S0439\":\"0.00\",\"S0438\":\"0.00\",\"S0437\":\"0\",\"S0436\":\"0\",\"S0435\":\"0\",\"S0434\":\"0.00\",\"S0132\":\"0\",\"S0422\":\"0.00\",\"S0130\":\"100%\",\"S0421\":\"0.00\",\"S0133\":\"0\",\"S0428\":\"0.00\",\"S0427\":\"0.00\",\"S0429\":\"0\",\"S0424\":\"0\",\"S0460\":\"0\",\"S0461\":\"0\",\"S0426\":\"0.00\",\"S0425\":\"0\",\"S0407\":\"0\",\"S0406\":\"0\",\"S0403\":\"0.00\",\"S0213\":\"0\",\"S0404\":\"0.00\",\"S0401\":\"0\",\"S0211\":\"0.00\",\"S0210\":\"0.00\",\"S0400\":\"0\",\"S0665\":\"浙江省建行营业部ATM_NA_1000.00\",\"S0214\":\"0\",\"S0085\":\"0_0\",\"S0451\":\"0.00\",\"S0455\":\"0\",\"S0454\":\"0\",\"S0452\":\"0.00\",\"S0082\":\"0_0.00\",\"S0109\":\"12\",\"S0108\":\"0\",\"S0458\":\"0.00\",\"S0457\":\"0.00\",\"S0492\":\"1\",\"S0416\":\"0.00\",\"S0418\":\"0\",\"S0419\":\"0\",\"S0412\":\"0\",\"S0413\":\"0\",\"S0480\":\"0.514\",\"S0415\":\"0.00\",\"S0410\":\"0.00\",\"S0516\":\"0.00\",\"S0113\":\"0\",\"S0112\":\"0\",\"S0440\":\"0.00\",\"S0111\":\"0\",\"S0442\":\"0\",\"S0441\":\"0\",\"S0444\":\"0.00\",\"S0443\":\"0\",\"S0481\":\"0.642\",\"S0447\":\"0\",\"S0409\":\"0.00\",\"S0023\":\"01_57%;03_29%;07_14%\",\"S0378\":\"0.00\",\"S0022\":\"00_0%\",\"S0020\":\"01;03;07\",\"S0237\":\"0\",\"S0234\":\"0.00\",\"S0379\":\"0.00\",\"S0231\":\"0\",\"S0166\":\"0\",\"S0167\":\"0\",\"S0532\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0225\":\"0\",\"S0228\":\"0.00\",\"S0221\":\"0\",\"S0174\":\"0%\",\"S0175\":\"0%\",\"S0533\":\"1611_NA;1610_NA;1609_NA;1608_NA;1607_河北永通电子科技有限公司;1606_NA;1605_NA;1604_NA;1603_NA;1602_NA;1601_NA;1512_NA\",\"S0220\":\"0\",\"S0420\":\"0.00\",\"S0267\":\"0\",\"S0264\":\"0.00\",\"S0261\":\"0\",\"S0423\":\"0\",\"S0408\":\"0.00\",\"S0390\":\"0.00\",\"S0405\":\"0\",\"S0391\":\"0.00\",\"S0258\":\"0.00\",\"S0189\":\"0\",\"S0392\":\"0.00\",\"S0186\":\"0.00\",\"S0402\":\"0.00\",\"S0217\":\"0.00\",\"S0252\":\"0.00\",\"S0216\":\"0.00\",\"S0250\":\"0\",\"S0251\":\"0\",\"S0219\":\"0\",\"S0218\":\"0.00\",\"S0255\":\"0\",\"S0450\":\"0.00\",\"S0453\":\"0\",\"S0459\":\"0\",\"S0398\":\"0.00\",\"S0495\":\"廊坊市_NA_银行银河路;廊坊市_广阳区_新华路\",\"S0397\":\"0.00\",\"S0183\":\"0\",\"S0180\":\"0.00\",\"S0399\":\"0\",\"S0456\":\"0.00\",\"S0394\":\"0\",\"S0393\":\"0\",\"S0396\":\"0.00\",\"S0003\":\"2\",\"S0395\":\"0\",\"S0247\":\"0.00\",\"S0248\":\"0.00\",\"S0381\":\"0\",\"S0417\":\"0\",\"S0249\":\"0\",\"S0195\":\"0\",\"S0198\":\"0.00\",\"S0202\":\"1\",\"S0414\":\"0.00\",\"S0201\":\"0\",\"S0204\":\"0.00\",\"S0240\":\"0.00\",\"S0203\":\"1\",\"S0205\":\"0.00\",\"S0411\":\"0\",\"S0243\":\"0\",\"S0208\":\"1\",\"S0151\":\"0\",\"S0207\":\"0\",\"S0152\":\"14;29\",\"S0153\":\"14;29\",\"S0246\":\"0.00\",\"S0019\":\"00\",\"S0389\":\"0_0\",\"S0192\":\"0.00\",\"S0388\":\"0_0\",\"S0387\":\"0_0\",\"S0386\":\"0_0.00\",\"S0385\":\"0_0.00\",\"S0384\":\"0_0.00\",\"S0051\":\"1611_0;1610_0;1609_0;1608_0;1607_1;1606_1;1605_0;1604_1;1603_0;1602_0;1601_0;1512_0\",\"S0382\":\"0\"},\"active\":null}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					 kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryQuota", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 17、逾期短信信息查询    返回值有问题 提示未知错误
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> phoneIsInBlacklist(String phone){
		Map<String ,String> map=new HashMap<String,String>();
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		map.put("phone", phone);
//		String kk="{\"errorCode\":407,\"errorMsg\":\"未知错误\",\"uid\":\"20161222173528244fNOPUjEuCJHmTFN\",\"data\":null}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("phoneIsInBlacklist");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":407,\"errorMsg\":\"未知错误\",\"uid\":\"20161222173528244fNOPUjEuCJHmTFN\",\"data\":null}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/verifyPhoneIsInBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/verifyPhoneIsInBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/verifyPhoneIsInBlacklist", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160912180520312vyCQuTQknMJQMVL\",\"data\":{ \"result\":\"1\", \"msg\":\"查询成功\",\"operatorType\": \"中国电信\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readIdPhoto");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160912180520312vyCQuTQknMJQMVL\",\"data\":{ \"result\":\"1\", \"msg\":\"查询成功\",\"operatorType\": \"中国电信\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileOperatorNameQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileOperatorNameQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileOperatorNameQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\": \"2012\",\"statusMsg\": {\"debitCardCount\": \"2\",\"creditCardCount\": \"0\",\"creditCardAging\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null},\"creditCardAge\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null}}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readPhoto");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\": \"2012\",\"statusMsg\": {\"debitCardCount\": \"2\",\"creditCardCount\": \"0\",\"creditCardAging\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null},\"creditCardAge\": {\"minIncluded\": null,\"maxIncluded\": null,\"min\": 0,\"max\": 0,\"unit\": null}}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryPhoneBankCardBindInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryPhoneBankCardBindInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryPhoneBankCardBindInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"paymentShortTermVoloatilityIndex\":\"0\",\"depositShortTermVoloatilityIndex\":\"-6\",\"depositLongTermVoloatilityIndex\":\"1\"}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readPhotoQuery");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"paymentShortTermVoloatilityIndex\":\"0\",\"depositShortTermVoloatilityIndex\":\"-6\",\"depositLongTermVoloatilityIndex\":\"1\"}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryActive ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryActive ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryActive ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"本数据库中未查得\",\"result\":{\"currentOutstandingLoanAmount\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"currentOutstandingLoanCount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"}}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readQueryLoadInfo");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"本数据库中未查得\",\"result\":{\"currentOutstandingLoanAmount\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"currentOutstandingLoanCount\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"}}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryLoadInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryLoadInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryLoadInfo ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data").get("result");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
		//测试数据  手机号绑定银行卡出入账查询
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"debitCardRemainingSum\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment3m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit3m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment12m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit12m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"}}}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("readIdPhotoAccount");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20160811101840779sEVSljbyBCjNiAC\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"debitCardRemainingSum\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment3m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit3m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"},\"debitCardPayment12m\":{\"minIncluded\":\"是\",\"maxIncluded\":\"否\",\"min\":8000,\"max\":9000,\"unit\":\"元\"},\"debitCardDeposit12m\":{\"minIncluded\":\"否\",\"maxIncluded\":\"否\",\"min\":-1,\"max\":-1,\"unit\":\"元\"}}}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/queryAccountChangeLines", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			//获取当前状态码
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss =  (int)Double.parseDouble(gg.get("errorCode").toString());
			// 获取返回码
			Map<String, Map<String, Object>> statusCode = gson.fromJson(kk, Map.class);
			int statuss = Integer.parseInt(statusCode.get("data").get("statusCode").toString());
			//获取返回结果
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			if(sss == 200){
				if(statuss == 2012){					
					data =re.get("data").get("result");
				} else {
					throw new BusinessException("查询失败");
				}
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
				e.getMessage();
				throw new BusinessException("查询失败");
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
//		String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileStatusQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
		try {
			int status = (int) RedisClusterUtils.getInstance().get("mobileStatusQuery");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileStatusQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileStatusQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileStatusQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212175927503EUbsfnUJYHXnZHD\",\"data\":{\"result\":\"1\",\"msg\":\"在网时长24个月以上\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("mobileOnlineIntervalQuery");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212175927503EUbsfnUJYHXnZHD\",\"data\":{\"result\":\"1\",\"msg\":\"在网时长24个月以上\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/mobileOnlineIntervalQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileOnlineIntervalQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/mobileOnlineIntervalQuery ", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				 data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212152955332fKVbNAVXbjRESdO\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\",\"idCardPhoto\":\"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\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("getIdPhoto");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212152955332fKVbNAVXbjRESdO\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\",\"idCardPhoto\":\"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\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/getIdPhoto", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/getIdPhoto", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/getIdPhoto", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			Map<String, Map<String, String>> re = gson.fromJson(kk, Map.class);
			int code = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			// 取出结果
			String checkStatus = re.get("data").get("checkStatus");
			// 请求成功
			if (code == 200) {
				if(checkStatus.equals("S")){					
					data = re.get("data");
				} else {
					throw new BusinessException("查询失败");
				}
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 7、学历查询     已停用
	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, String> verify_education(String idCard,String name,String levelNo) {
//		Map<String, String> map = new HashMap<String, String>();
//		Map<String, String> data = new HashMap<String, String>();
//		map.put("idCard", idCard);
//		map.put("name", name);
//		map.put("levelNo", levelNo);
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161210143154664llCfIKOZtUbURuT\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
//		try {
////			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/verify_education", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
//			Gson gson =new Gson();
//			Map<String,Map<String,Map<String,String>>> re=gson.fromJson(kk, Map.class);
//			Map<String,Object> gg=gson.fromJson(kk,Map.class);
//			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
//			if(sss==200){
//				data = re.get("data").get("checkResult");
//			}
//		} catch (Exception e) {
//			e.getMessage();
//		}
//		return data;
//	}
	/**
	 * 6、姓名-银行卡号一致性校验 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> bankcard2item(String bankCard,String name){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		map.put("name", name);
		map.put("bankCard", bankCard);
//		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223163314782ttGfPlOutECRxXD\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("bankcard2item");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223163314782ttGfPlOutECRxXD\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
					break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/bankcard2item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/bankcard2item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/bankcard2item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			String checkStatus = re.get("data").get("checkStatus");
			if(sss==200){
				if(checkStatus.equals("S")){					
					data = re.get("data");
				} else {
					throw new BusinessException("查询失败");
				}
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213175449320sCdBHzpDmXMMdHV\",\"data\":{\"result\":\"2\",\"msg\":\"身份证核查成功，但人脸比对失败\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("idNameImageCheck");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161213175449320sCdBHzpDmXMMdHV\",\"data\":{\"result\":\"2\",\"msg\":\"身份证核查成功，但人脸比对失败\"}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNameImageCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNameImageCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNameImageCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223174421480OmRSluLOSWgTnmo\",\"data\":{\"checkStatus\":\"F\",\"message\":\"认证信息不匹配\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("bankcard4item");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223174421480OmRSluLOSWgTnmo\",\"data\":{\"checkStatus\":\"F\",\"message\":\"认证信息不匹配\"}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/bankcard4item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/bankcard4item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/bankcard4item", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 3、姓名-身份证号-手机号一致性校验
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> idNamePhoneCheck(String idCard, String name, String phone){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("phone", phone);
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223180719952cyPanxXCeORljrQ\",\"data\":{\"result\":\"1\",\"msg\":\"一致\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("idNamePhoneCheck");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223180719952cyPanxXCeORljrQ\",\"data\":{\"result\":\"1\",\"msg\":\"一致\"}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNamePhoneCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNamePhoneCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNamePhoneCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223202611272XKCZUKIyKukmgmW\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("nameIdCardAccountVerify");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161223202611272XKCZUKIyKukmgmW\",\"data\":{\"checkStatus\":\"S\",\"message\":\"认证信息匹配\"}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/nameIdCardAccountVerify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerify", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 2M 姓名-身份证号-银行卡号一致性校验    有问题 无权限
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> nameIdCardAccountVerifyV(String idCard, String name, String bankCard){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
//		String kk="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("nameIdCardAccountVerifyV");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/nameIdCardAccountVerifyV", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerifyV", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerifyV", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}
	
	/**
	 * 2 F姓名-身份证号-银行卡号一致性校验
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> nameIdCardAccountVerifyF(String idCard, String name, String bankCard){
		Map<String, String> data = new HashMap<String, String>();
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("idCard", idCard);
		map.put("bankCard", bankCard);
//		String kk="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("nameIdCardAccountVerifyF");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":402,\"errorMsg\":\"没有调用接口的权限，无法访问\"}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/nameIdCardAccountVerifyF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerifyF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/nameIdCardAccountVerifyF", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
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
//		String kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212174359875JqqwsAteydQGLxy\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\"}}";
		try {
			int status = (int) RedisClusterUtils.getInstance().get("idNameCheck");
			SwitchStatus switchStatus = SwitchStatus.getStatus(status);
			String kk = "";
			switch (switchStatus) {
				case DEMO :
					kk="{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161212174359875JqqwsAteydQGLxy\",\"data\":{\"checkStatus\":\"S\",\"message\":\"一致\"}}";
					 break;
				case TEST:
					kk = HttpUtils.sendPost(Constants.TEST_URL + "/idNameCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
				case NORMAL:
					kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNameCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
					break;
			}
//			String kk = HttpUtils.sendPost(Constants.NORMAL_URL + "/idNameCheck", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson =new Gson();
			Map<String,Map<String,String>> re=gson.fromJson(kk, Map.class);
			Map<String,Object> gg=gson.fromJson(kk,Map.class);
			int sss=(int)(Double.parseDouble(gg.get("errorCode").toString()));
			if(sss==200){
				data = re.get("data");
			} else {
				throw new BusinessException("查询失败");
			}
		} catch (Exception e) {
			e.getMessage();
			throw new BusinessException("查询失败");
		}
		return data;
	}

	
}
