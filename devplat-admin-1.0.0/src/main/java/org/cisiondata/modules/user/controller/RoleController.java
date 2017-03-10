package org.cisiondata.modules.user.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.user.entity.AUserARole;
import org.cisiondata.modules.user.entity.RoleUser;
import org.cisiondata.modules.user.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController {

	@Resource(name = "reloe")
	private IRoleService iroleService;

	// 查询指定的某一条数据
	@ResponseBody
	@RequestMapping(value = "/certains/{id}")
	public WebResult getCertain(@PathVariable Long id) {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.readDataCertain(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	// 修改
	@ResponseBody
	@RequestMapping(value = "/updata")
	public WebResult getUpdata(RoleUser roleUser) {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.SoleJudgment(roleUser));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;

	}

	// 刪除
	@ResponseBody
	@RequestMapping("/Delet")
	public WebResult getDelet(long id) {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.DeleteJudgment(id));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	// 查詢用戶所有的數據
	@ResponseBody
	@RequestMapping("/getalluers")
	public WebResult getalluser() {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.getUser());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	// 新增到角色表的数据（）
	@ResponseBody
	@RequestMapping("/getaddRole")
	public WebResult getAddRole(RoleUser addRoleuser) {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.AddJudgment(addRoleuser));
			result.setCode(ResultCode.SUCCESS.getCode());				
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	// 获取到role的ID和标识信息
	@ResponseBody
	@RequestMapping("/UserROleID")
	public WebResult getRoleId(AUserARole auserRole) {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.roleuserID(auserRole));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	
	//验兑是否存在角色关联
	@ResponseBody
	@RequestMapping("/ROleIDS{userROID}/{findROleID}")
	public WebResult getIDfind(@PathVariable String userROID,@PathVariable long findROleID){
		WebResult result=new WebResult();
		try {
			result.setData(iroleService.findID(userROID,findROleID));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}		
		return result;
	}
	
	
	//中间表角色信息增加
	@ResponseBody
	@RequestMapping("/AddDelete/{addDelete}/{AddDeleteID}")
	public WebResult AddDelete(@PathVariable String addDelete,@PathVariable long AddDeleteID){
		WebResult result=new WebResult();
		try {
			result.setData(iroleService.AddDelete(addDelete, AddDeleteID));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	//中间表信息删除
	@ResponseBody
	@RequestMapping("/Deleteadd/{Deleteadd}/{DeleteaddID}")
	public WebResult Deleteadd(@PathVariable String Deleteadd,@PathVariable long DeleteaddID){
		WebResult result =new WebResult();
		try {
			result.setData(iroleService.Deleteadd(Deleteadd, DeleteaddID));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/role")
	public WebResult getAll() {
		WebResult result = new WebResult();
		try {
			result.setData(iroleService.readDataRole());
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ModelAndView toMoblie() {
		return new ModelAndView("/admin/role");
	}
}