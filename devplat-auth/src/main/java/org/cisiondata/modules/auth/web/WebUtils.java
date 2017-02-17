package org.cisiondata.modules.auth.web;

import org.cisiondata.modules.auth.Constants.CookieName;
import org.cisiondata.modules.auth.Constants.SessionName;
import org.cisiondata.modules.auth.dto.UserDTO;

public class WebUtils {

	public static UserDTO getCurrentUser() {
		UserDTO userDTO = null;
		WebContext webContext = WebContext.get();
		if (null != webContext && null != webContext.getSession()) {
			userDTO = webContext.getSession().getAttribute(SessionName.CURRENT_USER);
		} else {
			
		}
		return userDTO;
	}
	
	public static void setCurrentUser(UserDTO userDTO) {
		WebContext webContext = WebContext.get();
		if (null != webContext && null != webContext.getSession()) {
			webContext.getSession().setAttribute(SessionName.CURRENT_USER, userDTO);
		}
	}
	
	public static void removeCurrentUser() {
		WebContext webContext = WebContext.get();
		if (null != webContext && null != webContext.getSession()) {
			webContext.getSession().removeAttribute(SessionName.CURRENT_USER);
		}
	}
	
	public static String getAccountFromHead() {
		return getCookieValueFromHead(CookieName.USER_ACCOUNT);
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
