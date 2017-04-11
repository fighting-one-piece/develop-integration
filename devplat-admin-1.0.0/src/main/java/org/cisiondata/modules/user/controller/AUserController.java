package org.cisiondata.modules.user.controller;


import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.entity.AUser;
import org.cisiondata.modules.user.service.IAUserService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/orgrole")
public class AUserController {
	private Logger LOG = LoggerFactory.getLogger(AdminUserController.class);
	
	@Resource(name="aUserService")
	private IAUserService aUserService = null;

	//新增用户
	@RequestMapping(value="/addauser",method=RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public WebResult addAUser(String account,String password,String identity,String nickname,String expireTime,String salt){
		WebResult result=new WebResult();
		try {
			aUserService.addAUser(account,password,identity,nickname,expireTime,salt);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("新增成功");
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//分页查询全部
	@RequestMapping(value="/findauser",method = RequestMethod.GET)
	@ResponseBody
	public WebResult findAUser(Integer page,Integer pageSize){
		WebResult result=new WebResult();
		try {
			result.setData(aUserService.findAuser(page,pageSize));
			result.setCode(ResultCode.SUCCESS.getCode());
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//修改
	@RequestMapping(value="/uodateauser",method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public WebResult updateAUser(AUser auser){
		System.out.println("ID："+auser.getId());
		WebResult result=new WebResult();
		try {
			aUserService.updateAUser(auser);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("修改成功");
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//删除
	@RequestMapping(value="/deleteauser",method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public WebResult deleteAUser(Long id){
		WebResult result=new WebResult();
		AUser auser=new AUser();
		auser.setId(id);
		auser.setDeleteFlag(true);
		try {
			aUserService.updateAUser(auser);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("删除成功");
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	//根据账号添加秘钥
		@RequestMapping(value="/addkeyauser",method = RequestMethod.GET)
		@ResponseBody
	public WebResult findaccountAuser(String account){
		WebResult result=new WebResult();
		AUser auser=new AUser();
		auser.setAccount(account);
		try {
			aUserService.addkeyAuser(auser);
			result.setCode(ResultCode.SUCCESS.getCode());
			result.setData("添加密钥成功");
		}catch (BusinessException bu){
			result.setCode(bu.getCode());
			result.setFailure(bu.getDefaultMessage());
			LOG.error(bu.getDefaultMessage(),bu);
		}catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setData(e.getMessage());
			LOG.error(e.getMessage(), e);
		}	
		return result;
	}
}
