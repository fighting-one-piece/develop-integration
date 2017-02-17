package org.cisiondata.modules.auth.web.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 会话 */
public class Session {

    private static final Logger LOG = LoggerFactory.getLogger(Session.class);
    
    private String id = null;
    private SessionManager manager = null;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private CookieHandler cookieHandler = null;
    private StorageHandler storageHandler = null;
    private Map<String, Object> _localCache = null;

    protected Session() {
    }

    public Session(SessionManager manager, String id, HttpServletRequest request, HttpServletResponse response) throws SessionException {
        this.id = id;
        this.manager = manager;
        this.request = request;
        this.response = response;
        this.cookieHandler = manager.getCookieHandler();
        this.storageHandler = manager.getStorageHandler();
    }

    /**
     * 获取当前会话id
     *
     * @return 会话id
     */
    public String getId() {
        return id;
    }

    /**
     * 读取会话属性
     *
     * @param name - 属性名称
     * @return 属性值
     * @throws SessionException - 如果发生异常
     */
    @SuppressWarnings("unchecked")
	public <V extends Object> V getAttribute(String name) throws SessionException {
        try {
            Object value = null;
            if (_localCache != null) {
                value = _localCache.get(name);
            }
            if (value == null) {
                value = storageHandler.getAttribute(id, request, response, name);
            }
            return (V) value;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SessionException(e);
        }
    }
    
    /**
     * 读取会话属性
     *
     * @param name - 属性名称
     * @return 属性值 
     * @throws SessionException - 如果发生异常
     */
	@SuppressWarnings("unchecked")
	public <V extends Object> V getAttributeAndRemove(String name) throws SessionException {
		try {
			Object value = storageHandler.getAttributeAndRemove(id, request, response, name);
			return (V) value;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SessionException(e);
		}
	}

    /**
     * 保存会话属性
     *
     * @param name - 属性名称
     * @param value - 属性值
     * @throws SessionException - 如果发生异常
     */
    public void setAttribute(String name, Object value) throws SessionException {
        try {
            storageHandler.setAttribute(id, request, response, name, value);
            if (_localCache == null) {
                _localCache = new HashMap<String, Object>();
            }
            _localCache.put(name, value);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new SessionException(e);
        }
    }

    /**
     * 移除会话属性
     *
     * @param name - 属性名称
     * @throws SessionException - 如果发生异常
     */
    public void removeAttribute(String name) throws SessionException {
        try {
            storageHandler.removeAttribute(id, request, response, name);
            if (_localCache != null) {
                _localCache.remove(name);
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new SessionException(e);
        }
    }

    /**
     * 会话失效
     *
     * @param response - 响应
     * @throws SessionException - 如果发生异常
     */
    public void invalidate() throws SessionException {
        try {
            storageHandler.invalidate(id, request, response);
            cookieHandler.removeSessionId(request, response);
            
            id = storageHandler.createSessionId(request, response);
            cookieHandler.setSessionId(id, request, response);

            request.removeAttribute(SessionManager.REQUEST_SESSION_ATTRIBUTE_NAME);
            if (_localCache != null) {
                _localCache.clear();
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new SessionException(e);
        }
    }

    public SessionManager getManager() {
        return manager;
    }
}

