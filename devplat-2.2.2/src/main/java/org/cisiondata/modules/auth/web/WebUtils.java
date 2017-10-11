package org.cisiondata.modules.auth.web;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.auth.Constants;
import org.cisiondata.modules.auth.entity.AuthUser;
import org.cisiondata.modules.auth.service.IAuthUserService;
import org.cisiondata.modules.auth.web.session.SessionManager;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.cisiondata.utils.spring.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

	private static Logger LOG = LoggerFactory.getLogger(WebUtils.class);

	public static String getCurrentAccout() {
		AuthUser user = getCurrentUser();
		return null != user ? user.getAccount() : null;
	}

	public static AuthUser getCurrentUser() {
		AuthUser user = null;
		try {
			WebContext webContext = WebContext.get();
			if (null != webContext && null != webContext.getSession()) {
				user = webContext.getSession().getAttribute(Constants.SESSION_CURRENT_USER);
			}
			if (null == user) {
				String accessToken = getAccessTokenFromHead();
				if (!StringUtils.isBlank(accessToken)) {
					String sessionId = (String) RedisClusterUtils.getInstance().get(accessToken);
					if (!StringUtils.isBlank(sessionId)) {
						SessionManager sessionManager = SpringBeanFactory.getBean(SessionManager.class);
						Object accountObject = sessionManager.getStorageHandler().getAttribute(sessionId,
							webContext.getRequest(), webContext.getResponse(), Constants.SESSION_CURRENT_USER_ACCOUNT);
						LOG.info("WebUtils account: {}", accountObject);
						if (null != accountObject) {
							IAuthUserService userService = SpringBeanFactory.getBean(IAuthUserService.class);
							user = userService.readUserByAccount((String) accountObject);
						}
					}
				}
			}
			if (null == user) {
				String accessId = webContext.getRequest().getParameter("accessId");
				if (!StringUtils.isBlank(accessId)) {
					IAuthUserService userService = SpringBeanFactory.getBean(IAuthUserService.class);
					user = userService.readUserByAccessId(accessId);
				}
			}
			setCurrentUser(user);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		return user;
	}

	public static void setCurrentUser(AuthUser user) {
		WebContext webContext = WebContext.get();
		if (null != webContext && null != webContext.getSession()) {
			webContext.getSession().setAttribute(Constants.SESSION_CURRENT_USER, user);
		}
	}

	public static void removeCurrentUser() {
		WebContext webContext = WebContext.get();
		if (null != webContext && null != webContext.getSession()) {
			webContext.getSession().removeAttribute(Constants.SESSION_CURRENT_USER);
		}
	}

	public static String getAccessTokenFromHead() {
		return null == WebContext.get() || null == WebContext.get().getRequest() ? 
			null : WebContext.get().getRequest().getHeader("accessToken");
	}

	public static String getAccountFromHead() {
		return getCookieValueFromHead(Constants.COOKIE_USER_ACCOUNT);
	}

	public static String getCookieValueFromHead(String cookieKey) {
		String cookieValue = "";
		String cookieInfo = WebContext.get().getRequest().getHeader("Cookie");
		String[] cookies = cookieInfo.split(";");
		for (int i = 0, len = cookies.length; i < len; i++) {
			String cookie = cookies[i];
			if (cookie.indexOf("=") != -1) {
				String[] cookieKV = cookie.split("=");
				if (cookieKey.equals(cookieKV[0].trim())) {
					cookieValue = cookieKV.length == 2 ? cookieKV[1] : "";
				}
			}
		}
		return cookieValue;
	}

}
