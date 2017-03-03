package org.cisiondata.modules.datainterface.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.datainterface.entity.AccessUserInterface;
import org.cisiondata.modules.datainterface.service.IAccessUserInterfaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户接口表Controller
 * @author Administrator
 *
 */
@Controller
public class AccessUserInterfaceController {

	@Resource(name="accessUserInterfaceService")
	private IAccessUserInterfaceService  accessUserInterfaceService;
	
	@ResponseBody
	@RequestMapping("/AccessUserInterface/Pagination")
	public WebResult AccessUserFindAll(int page,int pageSize){
		WebResult result=new WebResult();
		try {
			result.setData(accessUserInterfaceService.Pagination(page,pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	//通过ID号查询
		@ResponseBody
		@RequestMapping("/AccessUserInterface/QueryId/{update_id}")
		public WebResult QueryId(@PathVariable long update_id){
			WebResult result =new WebResult();
			try {
				result.setData(accessUserInterfaceService.QueryId(update_id));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	//修改用户接口		//0 为 false  不收费    1位true  收费
		@ResponseBody
		@RequestMapping("/AccessUserInterface/{Number}/{id}/Interface")
		public WebResult Revamp(@PathVariable int Number,@PathVariable  long id){
			WebResult result =new WebResult();
			try {
				result.setData(accessUserInterfaceService.Revamp(Number, id));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	//删除用户
		@ResponseBody
		@RequestMapping("/AccessUserInterface/RemoveUser/{id}")
		public WebResult RemoveUser(@PathVariable long id){
			WebResult result =new WebResult();
			try {
				result.setData(accessUserInterfaceService.RemoveUser(id));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	//用户账号数据
		@ResponseBody
		@RequestMapping("/AccessUserInterface/userAccount")
		public WebResult userAccount(){
			WebResult result =new WebResult();
			try {
				result.setData(accessUserInterfaceService.userAccount());
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	  //接口账号数据
		@ResponseBody
		@RequestMapping("/AccessUserInterface/hickey")
		public WebResult hickey(){
			WebResult result=new WebResult();
			try {
				result.setData(accessUserInterfaceService.hickey());
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;
		}
	 //增加用户账号
		@ResponseBody
		@RequestMapping("/AccessUserInterface/add")
		public WebResult  addUserAccount(AccessUserInterface UserInterface){
			WebResult result=new WebResult();
			try {
				result.setData(accessUserInterfaceService.addUserAccount(UserInterface));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			
			return result;
		}
		
	//批量增加账户接口
		@ResponseBody
		@RequestMapping("/AccessUserInterface/giveHickey/{count}")
		 public WebResult giveHickey(@PathVariable String account){
			WebResult result=new WebResult();
			try {
				result.setData(accessUserInterfaceService.giveHickey(account));
				result.setCode(ResultCode.SUCCESS.getCode());
			} catch (Exception e) {
				result.setCode(ResultCode.FAILURE.getCode());
				result.setFailure(e.getMessage());
			}
			return result;	 
		 }
		
	@RequestMapping(value="/AccessUserInterface")
	public ModelAndView toMoblie() {
		return new ModelAndView("admin/accessUserInterface");
	}
}
