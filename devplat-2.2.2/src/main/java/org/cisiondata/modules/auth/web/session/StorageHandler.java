package org.cisiondata.modules.auth.web.session;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 会话存储器, 负责存储会话数据 */
public interface StorageHandler extends Serializable {
	
	/**
	 * 创建新的会话id
	 * @param request - 请求
	 * @param response - 响应
	 * @return 会话id
	 * @throws SessionException - 如果发生会话异常
	 */
	public String createSessionId(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 判断会话id是否可用
	 * @param sessionId - 会话id
	 * @return
	 * @throws Exception - 如果发生异常
	 */
	public boolean existsSessionId(String sessionId) throws Exception;

	/**
	 * 初始化会话
	 * @param sessionId - 会话id
	 * @throws SessionException - 如果发生会话异常
	 */
	public void initialize(String sessionId) throws Exception;

	/**
	 * 会话失效
	 * @param sessionId - 会话id
	 * @param request - 请求
	 * @param response - 响应
	 * @throws SessionException - 如果发生会话异常
	 */
	public void invalidate(String sessionId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception;

	/**
	 * 保存会话属性
	 * @param sessionId - 会话id
	 * @param name - 属性名称
	 * @param value - 属性值
	 * @throws SessionException - 如果发生会话异常
	 */
	public void setAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name, Object value) throws Exception;

	/**
	 * 加载会话属性
	 * @param sessionId - 会话id
	 * @param name - 属性名称
	 * @return 属性值
	 * @throws SessionException - 如果发生会话异常
	 */
	public Object getAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception;
	
	/**
	 * 加载会话属性
	 * @param sessionId - 会话id
	 * @param name - 属性名称
	 * @return 属性值
	 * @throws SessionException - 如果发生会话异常
	 */
	public Object getAttributeAndRemove(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception;

	/**
	 * 删除会话属性
	 * @param sessionId - 会话id
	 * @param name - 属性名称
	 * @throws SessionException - 如果发生会话异常
	 */
	public void removeAttribute(String sessionId, HttpServletRequest request, 
			HttpServletResponse response, String name) throws Exception;
}
