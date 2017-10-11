package org.cisiondata.modules.auth.service.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.cisiondata.modules.auth.web.WebUtils;
import org.cisiondata.modules.queue.entity.RequestMessage;
import org.cisiondata.modules.rabbitmq.service.IRabbitmqService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.web.IPUtils;
import org.cisiondata.utils.web.URLFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("resultMQService")
public class ResultMQServiceImpl extends RequestServiceImpl {

	private Logger LOG = LoggerFactory.getLogger(ResultMQServiceImpl.class);

	@Resource(name = "rabbitmqService")
	private IRabbitmqService rabbitmqService = null;

	@Override
	public Object[] postHandle(HttpServletRequest request, Object result) throws BusinessException {
		String requestUrl = request.getServletPath();
		if (requestUrl.startsWith("/api/v1")) {
			requestUrl = requestUrl.replace("/api/v1", "");
		}
		if(URLFilter.mqFilterUrl(requestUrl)){
			try {
				RequestMessage requestMessage = wrapperRequestMessage(request, requestUrl, result);
				rabbitmqService.sendTopicMessage("topic.sync", requestMessage);
			} catch (Exception e) {
				LOG.error("result: {}", result);
				LOG.error(e.getMessage(), e);
			}
		}
		return super.postHandle(request, result);
	}

	@SuppressWarnings({ "unchecked" })
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
			if (!attributeName.startsWith("rda_"))
				continue;
			requestMessage.getAttributes().put(attributeName, request.getAttribute(attributeName));
		}
		requestMessage.setIpAddress(IPUtils.getIPAddress(request));
		requestMessage.setAccount(WebUtils.getCurrentAccout());
		requestMessage.setTime(new Date());
		requestMessage.setReturnResult(result);
		return requestMessage;
	}

}
