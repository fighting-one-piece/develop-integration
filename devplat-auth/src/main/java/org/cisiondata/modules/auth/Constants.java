package org.cisiondata.modules.auth;

public class Constants {
	
	/**
     * 当前登录的用户
     */
    public static final String CURRENT_USER = "user";
    public static final String CURRENT_ACCOUNT = "account";
    public static final String CURRENT_USERNAME = "username";
    public static final String CURRENT_NICKNAME = "nickname";
    
    /**
     * 当前在线会话
     */
   public static final String ONLINE_SESSION = "online_session";

    /**
     * 仅清空本地缓存 不情况数据库的
     */
    public static final String ONLY_CLEAR_CACHE = "online_session_only_clear_cache";
    
    /**
     * 操作名称
     */
    public static final String OP_NAME = "op";

    /**
     * 消息key
     */
    public static final String MESSAGE = "message";

    /**
     * 错误key
     */
    public static final String ERROR = "error";

    /**
     * 上个页面地址
     */
    public static final String BACK_URL = "BackURL";

    public static final String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    public static final String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    public static final String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";

    public static final String CONTEXT_URL = "contextURL";

    /**
     * 编码
     */
    public static final String ENCODING = "UTF-8";
    
    public interface SessionName {
   	 	public static final String CURRENT_USER = "_CURRENT_USER_";
        public static final String CURRENT_USER_ACCOUNT = "_CURRENT_USER_ACCOUNT_";
        public static final String CURRENT_USER_RIGHTS = "_CURRENT_USER_RIGHTS_";
        public static final String VERIFICATION_CODE = "_VERIFICATION_CODE_";
    }
    
    public interface CookieName {
        public static final String USER_ACCOUNT = "_ua_";
        public static final String USER_SESSION = "_session_";
        public static final String USER_ACCESS_TOKEN = "accessToken";
    }
    
}
