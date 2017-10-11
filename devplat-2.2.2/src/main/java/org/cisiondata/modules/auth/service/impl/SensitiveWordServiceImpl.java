package org.cisiondata.modules.auth.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("sensitiveWordService")
public class SensitiveWordServiceImpl extends RequestServiceImpl implements InitializingBean {

	Set<String> notFilterPath = new HashSet<String>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		notFilterPath.add("/users/settings/profile");
		notFilterPath.add("/users/account/verify");
		notFilterPath.add("/users/sms");
		notFilterPath.add("/users/sms/verify");
		notFilterPath.add("/users/mobliePhone/verify");
		notFilterPath.add("/users/settings/newphone");
		notFilterPath.add("/users/settings/security/verify");
		notFilterPath.add("/users/settings/newpassword");
		notFilterPath.add("/users/security/questions");
		notFilterPath.add("/users/settings/newphone/sms");
		notFilterPath.add("/users/settings/security/question");
		notFilterPath.add("/users/settings/newsecurity");
		notFilterPath.add("/users/settings/security");
		notFilterPath.add("/users/settings/profile");
		notFilterPath.add("/users/settings/forget/newpassword");
		notFilterPath.add("/users/opinion");
		notFilterPath.add("/login");
		notFilterPath.add("/verificationCode.jpg");
		notFilterPath.add("/collect/add");
		notFilterPath.add("/collect/update");
		notFilterPath.add("/collect/delete");
		notFilterPath.add("/collect/query");
		notFilterPath.add("/collectdetail/add");
		notFilterPath.add("/collectdetail/delete");
		notFilterPath.add("/collectdetail/query");
		notFilterPath.add("/login/jyt");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] preHandle(HttpServletRequest request) throws BusinessException {
		String path = request.getServletPath();
		if (path.startsWith("/api/v1")) {
			path = path.replace("/api/v1", "");
			if (notFilterPath.contains(path)) return new Object[]{true};
		}
		try {
			judgeSensitiveWord(request.getParameterMap());
		} catch (BusinessException be) {
			LOG.error(be.getMessage(), be);
			return new Object[]{false, be};
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return new Object[]{false, new BusinessException(e.getMessage())};
		}
		return new Object[]{true};
	}
	
	@SuppressWarnings("unchecked")
	private void judgeSensitiveWord(Map<String, String[]> paramMap) throws BusinessException {
    	for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
    		Object arg = entry.getValue()[0];
			if (arg instanceof Map) {
				Map<String, Object> argMap = (Map<String, Object>) arg;
				for (Map.Entry<String, Object> argEntry : argMap.entrySet()) {
					judgeSensitiveWord(argEntry.getValue());
				}
			} else {
				judgeSensitiveWord(arg);
			}
    	}
	}
    
    @SuppressWarnings({ "unchecked", "unused" })
	private void judgeSensitiveWord(Object[] args) throws BusinessException {
		if (null != args && args.length != 0) {
			for (int i = 0, len = args.length; i < len; i++) {
				Object arg = args[i];
				if (arg instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) arg;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						judgeSensitiveWord(entry.getValue());
					}
				} else {
					judgeSensitiveWord(arg);
				}
			}
		}
	}
	
	private void judgeSensitiveWord(Object arg) throws BusinessException {
		if (arg instanceof String) {
			String queryTxt = String.valueOf(arg);
			if(queryTxt.indexOf(":") != -1) {
				String[] args = queryTxt.split(",");
				for(String word : args){
					String[] keywords = word.split(":");
					for (int i = 0, len = keywords.length; i < len; i++) {
						if (RedisClusterUtils.getInstance().sismember("sensitive_word", keywords[i])) {
							throw new BusinessException(ResultCode.QUERY_INVOLVE_SENSITIVE_WORDS);
						}
					}
				}
			} else {
				String[] keywords = queryTxt.indexOf(" ") == -1 ? new String[]{queryTxt} : queryTxt.split(" ");
				for (int i = 0, len = keywords.length; i < len; i++) {
					if (RedisClusterUtils.getInstance().sismember("sensitive_word", keywords[i])) {
						throw new BusinessException(ResultCode.QUERY_INVOLVE_SENSITIVE_WORDS);
					}
				}
			}
			
		}
	}
}
