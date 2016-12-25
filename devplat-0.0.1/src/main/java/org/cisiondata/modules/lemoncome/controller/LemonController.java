package org.cisiondata.modules.lemoncome.controller;


import java.util.HashMap;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

//@Controller
@RequestMapping("/lemoncome")
public class LemonController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/lemoneducation")
	@ResponseBody
	public WebResult readEducation(String idCard,String name,String levelNo) {
		WebResult result = new WebResult();
		Map<String, String> map =new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("name", name);
		map.put("levelNo", levelNo);
		//测试JSON
		String kk = "{\"errorCode\":200,\"errorMsg\":\"请求成功\",\"uid\":\"20161210143154664llCfIKOZtUbURuT\",\"data\":{\"message\":\"交易成功\",\"checkStatus\":\"S\",\"checkResult\":{\"degree\":{\"isKeySubject\":\"N\",\"startTime\":\"20120901\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"studyResult\":\"毕业\",\"studyType\":\"研究生\",\"studyStyle\":\"全日制\",\"college\":\"北京理工大学\",\"levelNo\":\"100071201502001019\",\"photo\":\"\",\"specialty\":\"计算机科学与技术\",\"photoStyle\":\"JPG\"},\"personBase\":{\"originalAddress\":\"江西省景德镇市乐平市\",\"riskAndAdviceInfo\":\"1.此类人群工资收入处于中高水平，在有大专以上学历人群中，按工资收入水平从高到低排位，约在前10%至前35%左右。2.此类人群违约率普遍较低，在有大专以上学历人群中，按违约率从高到低排位，约在后10%左右。3.此类人群属于中高收入、低风险人群。4.此类人群建议给予较高程度的授信。\",\"birthday\":\"19910514\",\"graduateTime\":\"2015\",\"degree\":\"硕士研究生\",\"age\":\"25\",\"name\":\"周磊\",\"college\":\"北京理工大学\",\"graduateYears\":\"1\",\"gender\":\"1\",\"specialty\":\"计算机科学与技术\",\"verifyResult\":\"1\",\"documentNo\":\"360281199105148039\"},\"college\":{\"colgCharacter\":\"普通高等教育\",\"colgLevel\":\"本科\",\"postDoctorNum\":\"19\",\"is211\":\"Y\",\"masterDegreeNum\":\"144\",\"college\":\"北京理工大学\",\"colgType\":\"工科\",\"scienceBatch\":\"本科第一批\",\"character\":\"公办\",\"createYears\":\"76\",\"manageDept\":\"工业和信息化部\",\"academicianNum\":\"15\",\"address\":\"北京市\",\"collegeOldName\":\"北京理工大学\",\"keySubjectNum\":\"16\",\"doctorDegreeNum\":\"61\",\"createDate\":\"1940\",\"artBatch\":\"本科第一批\"},\"edu_result\":\"0\"}}}";
		try {
			//调用学历API
//			String kk = HttpUtils.sendPost(Constants.TEST_URL + "/education/organizeD", map, Constants.APP_KEY, Constants.APP_KEY_VALUE);
			Gson gson = new Gson();
			Map<String, Map<String,Map<String,String>>> re = gson.fromJson(kk, Map.class);
			Map<String, Object> gg = gson.fromJson(kk, Map.class);
			int sss = (int)(Double.parseDouble(gg.get("errorCode").toString()));
			if (sss == 200) {
				result.setData(re.get("data").get("checkResult"));
				result.setCode(ResultCode.SUCCESS.getCode());
				System.out.println(ResultCode.SUCCESS.getCode());
			}else {
				result.setCode(ResultCode.FAILURE.getCode());
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String lemon() {
		return "lemoncome/lemon";
	}
	
	@RequestMapping(value="/education",method=RequestMethod.GET)
	public String lemonEducation() {
		return "lemoncome/lemoneducation";
	}
}
