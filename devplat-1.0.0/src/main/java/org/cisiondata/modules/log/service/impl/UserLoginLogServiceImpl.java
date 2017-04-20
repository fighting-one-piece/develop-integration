package org.cisiondata.modules.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.log.dao.UserLoginLogDAO;
import org.cisiondata.modules.log.entity.UserLoginLog;
import org.cisiondata.modules.log.service.IUserLoginLogService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.web.Head;
import org.cisiondata.utils.web.IPUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("userLoginLogService")
public class UserLoginLogServiceImpl implements IUserLoginLogService,InitializingBean{
	
	@Resource(name = "userLoginLogDAO")
	private UserLoginLogDAO logDAO = null;
	private List<Object> listHead = new ArrayList<Object>();
	@Override
	public void afterPropertiesSet() throws Exception {
		Head head = new Head();
		head.setField("account");
		head.setFieldName("用户账号");
		listHead.add(head);
		head = new Head();
		head.setField("ip");
		head.setFieldName("IP地址");
		listHead.add(head);
		head = new Head();
		head.setField("currentTime");
		head.setFieldName("时间");
		listHead.add(head);
		head = new Head();
		head.setField("status");
		head.setFieldName("状态");
		listHead.add(head);
	}
	//新增
	public void addUserLoginLog(String account,HttpServletRequest request,int status) throws BusinessException {
		UserLoginLog log = new UserLoginLog();
		//获取当前IP
		String ip = IPUtils.getIPAddress(request);
		if(StringUtils.isBlank(account)){
			account = WebUtils.getCurrentAccout();
		}
		if(!ip.equals("0:0:0:0:0:0:0:1") && StringUtils.isNotBlank(account)){
			log.setAccount(account);
			log.setIp(ip);
			log.setCurrentTime(new Date());
			log.setStatus(status);
			logDAO.addLoginLog(log);
		}
	}
	
	//查询用户上一次登录时间
	public String readUserLoginLog(String account) throws BusinessException {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserLoginLog userLoginLog = logDAO.selLoginLog(account);
		if (null == userLoginLog) return dateFormater.format(new Date());
		return dateFormater.format(userLoginLog.getCurrentTime());
	}
	//查询用户登录日志进行分页
	public Map<String, Object> selLoginLogPage(String account, int index, int pageSize) throws BusinessException {
		System.out.println("账号："+account+",index:"+index+",pageSize:"+pageSize);
		int pageCount = 0;
		QueryResult<Map<String,Object>> result = new QueryResult<Map<String,Object>>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<UserLoginLog> list = logDAO.selLoginLogCount(account);
		System.out.println("查询总数："+list.size());
		if(list.size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		if(index > 0){
			int page = (index -1) * pageSize;
			pageCount = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
			List<UserLoginLog> lists = logDAO.selLoginLogPage(account, page, pageSize);
			System.out.println("lists数据："+lists);
			if(lists.size() > 0){
				List<Map<String,Object>>  listMap = new ArrayList<Map<String,Object>>();
				for (int i = 0,len = lists.size(); i < len; i++) {
					Map<String,Object> mapList = new HashMap<String,Object>();
					mapList.put("account", lists.get(i).getAccount());
					mapList.put("ip", lists.get(i).getIp());
					mapList.put("currentTime",lists.get(i).getCurrentTime());
					if(lists.get(i).getStatus() == 0){
						mapList.put("status", "登陆");
					}
					if(lists.get(i).getStatus() == 1){
						mapList.put("status", "登出");
					}
					listMap.add(mapList);
				}
				result.setTotalRowNum(pageCount);
				result.setResultList(listMap);
				mapResult.put("data", result);
				mapResult.put("head", listHead);
				System.err.println(mapResult);
			}else{
				throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
			}
		}else{
			throw new BusinessException(ResultCode.PARAM_FORMAT_ERROR);
		}
		
		return mapResult;
	}

	

}
