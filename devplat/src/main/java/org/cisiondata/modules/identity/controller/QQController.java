package org.cisiondata.modules.identity.controller;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.elasticsearch.service.IESBizService;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IQQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/qq")
public class QQController {
	
	private Logger LOG = LoggerFactory.getLogger(QQController.class);
	
	@Autowired
	private IQQService qqService = null;
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	@Resource(name = "esBizService")
	private IESBizService esBizService = null;
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
	
	
//	//根据QQ号码查询对应的群信息
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
	@RequestMapping(value = "/base/search")
	public WebResult readQQData(@RequestParam String qqNum){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = qqService.readQQData(qqNum);
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
	
	@ResponseBody
	@RequestMapping(value="/qun/search")
	//得到群信息
	public WebResult readQQqunDatas(@RequestParam String qunNum){
		WebResult result = new WebResult();
		try {
			List<Map<String, Object>> list = qqService.readQQqunDatas(qunNum);
			Map<String, Object> map = qqService.readQQqun(qunNum);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).remove("更新时间");
				list.get(i).remove("源文件");
				list.get(i).remove("录入时间");
				list.get(i).remove("index");
				list.get(i).remove("type");
				for (Map.Entry<String, Object> entry:map.entrySet()) {
					list.get(i).put(entry.getKey(), entry.getValue());
				}
			}
			result.setData(list);
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/quns/search")
	//得到群信息
	public WebResult readQQqunsDatas(String[] qunNumList){
		WebResult result = new WebResult();
		try {
			System.out.println(qunNumList);
			
			result.setData(esService.readDataListByCondition("qq", "qqqunrelation", "qunNum", Arrays.asList(qunNumList), 200));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	//QQ查询页面
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView toMoblie(){
		return new ModelAndView("/qq/qqQuery");
	}
}
