package org.cisiondata.modules.system.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.system.service.ISensitiveWordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 敏感词
 * @author Administrator
 *
 */

@Controller
public class SensitiveController {
	
	@Resource(name="sensitiveWordService")
	private ISensitiveWordService sensitiveWordService;
	
	//实现ID 查询相应的信息
	@ResponseBody
	@RequestMapping(value="/findid{ID}")
	public WebResult FindID(@PathVariable long ID){
		WebResult result=new WebResult();
		try {
			result.setData(sensitiveWordService.FindID(ID));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//修改数据
	@ResponseBody
	@RequestMapping(value="/updataID/{updateid}/{updateCount}/{updateInitialCount}")
	public WebResult updataID(@PathVariable long updateid,@PathVariable String updateCount,@PathVariable String updateInitialCount){
		WebResult result =new WebResult();
		try {
			result.setData(sensitiveWordService.SoleJudgment(updateid,updateCount,updateInitialCount));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	//新增敏感词
	@ResponseBody
	@RequestMapping(value="/AddSensitive")
	public WebResult AddSensitive(String word){
		WebResult result=new WebResult();
		 try {
			result.setData(sensitiveWordService.AddJudgment(word));
			result.setCode(ResultCode.SUCCESS.getCode()); 
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
		
	}
	//删除操作
	@ResponseBody
	@RequestMapping(value="/Sendelete/{deleteId}/{deleteCount}")
	public WebResult Sendelete(@PathVariable long  deleteId,@PathVariable String deleteCount){
		WebResult result=new WebResult();
		try {
			result.setData(sensitiveWordService.Sendelete(deleteId,deleteCount));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			// TODO: handle exception
		}
		return result;
	}
	
	//显示所有
	@ResponseBody
	@RequestMapping(value="/SensitiveAll")
	public WebResult SensitiveAll(int page,int pageSize){
		System.out.println(page);
		System.out.println(pageSize);
		WebResult result=new WebResult();
		try {
			result.setData(sensitiveWordService.SensitiveAll(page,pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	//敏感词管理页面
	@RequestMapping(value="/sensitive",method = RequestMethod.GET)
	public ModelAndView toMoblie() {
		return new ModelAndView("admin/sensitive");
	}
}
