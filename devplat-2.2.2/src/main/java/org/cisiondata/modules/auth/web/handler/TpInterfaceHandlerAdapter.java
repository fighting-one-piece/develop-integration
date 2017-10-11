package org.cisiondata.modules.auth.web.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.cisiondata.utils.http.HttpUtils;
import org.cisiondata.utils.properties.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class TpInterfaceHandlerAdapter implements InitializingBean {
	
	private Logger LOG = LoggerFactory.getLogger(TpInterfaceHandlerAdapter.class);
	
	private Map<String, String> url_mapping = new HashMap<String, String>();
	
	private static final String URL = PropertiesUtils.getProperty(
			"tpinterface/env.local.properties", "tpinterface.url");
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = TpInterfaceHandlerAdapter.class.getClassLoader().getResourceAsStream("tpinterface/urlmapping.properties");
			properties.load(in);
			for (Entry<Object, Object> entry : properties.entrySet()) {
				url_mapping.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
				LOG.info("key {} value {}", String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != in) in.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		
	}
	
	public boolean isThirdpartInterface(String path) {
		path = path.replaceAll("^/app", "").replaceAll("^/ext", "").replaceAll("^/api/v1", "");
		return url_mapping.containsKey(path);
	}
	
	@SuppressWarnings("unchecked")
	public Object handle(HttpServletRequest request) {
		String requestUrl = request.getServletPath();
		String tpinterface_access_type = null;
		if (requestUrl.startsWith("/api/v1")) {
			requestUrl = requestUrl.replaceAll("^/api/v1", "");
			tpinterface_access_type = "org";
		} else if (requestUrl.startsWith("/app")) {
			requestUrl = requestUrl.replaceAll("^/app", "").replaceAll("^/api/v1", "");
			tpinterface_access_type = "app";
		} else if (requestUrl.startsWith("/ext")) {
			requestUrl = requestUrl.replaceAll("^/ext", "").replaceAll("^/api/v1", "");
			tpinterface_access_type = "ext";
		}
		String thirdpartRequestUrl = readThirdpartRequestUrl(requestUrl);
		LOG.info("requestUrl: {} thirdpartRequestUrl: {}", requestUrl, thirdpartRequestUrl);
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tpinterface_access_type", tpinterface_access_type);
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue()[0]);
		}
		String result = HttpUtils.sendGet(thirdpartRequestUrl, params);
		LOG.info("thirdpart result: " + result);
		return result;
	}
	
	private String readThirdpartRequestUrl(String requestUrl) {
		return URL + url_mapping.get(requestUrl);
	}

}
