package org.cisiondata.modules.auth.service.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.queue.entity.MQueue;
import org.cisiondata.modules.queue.entity.RequestMessage;
import org.cisiondata.modules.queue.service.IRedisMQService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.web.IPUtils;
import org.springframework.stereotype.Service;

@Service("resultMQService")
public class ResultMQServiceImpl extends RequestServiceImpl {
	
	@Resource(name = "redisMQService")
	private IRedisMQService redisMQService = null;

	@Override
	public Object[] postHandle(HttpServletRequest request, Object result) throws BusinessException {
		String requestUrl = request.getServletPath();
		if (requestUrl.startsWith("/api/v1")) {
			requestUrl = requestUrl.replace("/api/v1", "");
		}
		redisMQService.sendMessage(MQueue.REQUEST_ACCESS_QUEUE.getRoutingKey(), 
				wrapperRequestMessage(request, requestUrl, result));
		return super.postHandle(request, result);
	}
	
	@SuppressWarnings({"unchecked"})
	private RequestMessage wrapperRequestMessage(HttpServletRequest request, String requestUrl, Object result) {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setUrl(requestUrl);
		Map<String, String[]> requestParams = request.getParameterMap();
    	for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			requestMessage.getParams().put(entry.getKey(), entry.getValue()[0]);
    	}
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			if (!attributeName.startsWith("rda_")) continue;
			requestMessage.getAttributes().put(attributeName, request.getAttribute(attributeName));
		}
		requestMessage.setIpAddress(IPUtils.getIPAddress(request));
		requestMessage.setAccount(WebUtils.getCurrentAccout());
		requestMessage.setTime(new Date());
		requestMessage.setReturnResult(result);
		return requestMessage;
	}
	
}
