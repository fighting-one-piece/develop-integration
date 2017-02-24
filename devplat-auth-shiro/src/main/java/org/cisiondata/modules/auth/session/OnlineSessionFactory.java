package org.cisiondata.modules.auth.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.cisiondata.modules.auth.entity.UserOnline;
import org.cisiondata.utils.web.IPUtils;

/**
 * 创建自定义的session，
 * 添加一些自定义的数据
 * 如 用户登录到的系统ip
 * 用户状态（在线 隐身 强制退出）
 * 等 比如当前所在系统等
 */
public class OnlineSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext sessionContext) {
        OnlineSession onlineSession = new OnlineSession();
        if (sessionContext != null && sessionContext instanceof WebSessionContext) {
            WebSessionContext webSessionContext = (WebSessionContext) sessionContext;
            HttpServletRequest request = (HttpServletRequest) webSessionContext.getServletRequest();
            if (request != null) {
                onlineSession.setHost(IPUtils.getIPAddress(request));
                onlineSession.setUserAgent(request.getHeader("User-Agent"));
                onlineSession.setSystemHost(request.getLocalAddr() + ":" + request.getLocalPort());
            }
        }
        return onlineSession;
    }

    public Session createSession(UserOnline userOnline) {
        return userOnline.getSession();
    }
}
