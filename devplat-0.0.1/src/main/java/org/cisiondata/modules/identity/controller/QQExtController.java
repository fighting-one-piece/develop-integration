package org.cisiondata.modules.identity.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.identity.service.IQQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ext/qq")
public class QQExtController {
	
	private Logger LOG = LoggerFactory.getLogger(QQController.class);
	
	@Autowired
	private IQQService qqService = null;
	
	@ResponseBody
	@RequestMapping(value = "/search")
	//根据QQ号码查询基础信息和对应的群信息
	public WebResult readQQ(@RequestParam String qqNum){
		WebResult result = new WebResult();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			//得到基本信息
			List<Map<String, Object>> listBase = qqService.readQQData(qqNum);
			for (int i = 0; i < listBase.size(); i++) {
				listBase.get(i).remove("更新时间");
				listBase.get(i).remove("源文件");
				listBase.get(i).remove("录入时间");
				listBase.get(i).remove("index");
				listBase.get(i).remove("type");
			}
			//得到群信息
			List<Map<String, Object>> qun = qqService.readQQDatas(qqNum);
			map.put("qqBase", listBase);
			map.put("qun", qun);
			result.setData(map);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	
	//根据QQ号码查询对应的群信息
//	@ResponseBody
//	@RequestMapping(value="/qun/search")
//	public WebResult readQQDatas(@RequestParam String qqNum){
//		WebResult result = new WebResult();
//		try {
//			result.setData(qqService.readQQDatas(qqNum));
//			result.setCode(ResultCode.SUCCESS.getCode());
//		} catch (Exception e) {
//			result.setCode(ResultCode.FAILURE.getCode());
//			result.setFailure(e.getMessage());
//		}
//		return result;
//	}
	
	@ResponseBody
	@RequestMapping(value="/nickname/search")
	//通过QQ昵称得到群信息
	public WebResult readQQNickDatas(String nickname,String scrollId, int rowNumPerPage){
		LOG.info("qq nickname : {}", nickname);
		WebResult result = new WebResult();
		try {
			result.setData(qqService.readQQNickData(nickname,scrollId,rowNumPerPage));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/qun/search")
	//得到群信息
	public WebResult readQQqunDatas(@RequestParam String qunNum){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = qqService.readQQqunDatas(qunNum);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("源文件");
				list.get(i).remove("录入时间");
				list.get(i).remove("index");
				list.get(i).remove("type");
			}
			result.setData(list);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
}
