package org.cisiondata.modules.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.cisiondata.modules.user.service.IMessageService;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Service("messageService")
public class MessageServiceImpl implements IMessageService{
	//发送短信正式URL
	private static String normalUrl = "https://eco.taobao.com/router/rest"; 
	//App Key
	private static String appkey = "23716955"; 
	//App Secret
	private static String secret = "0b86f342a0cb664694b459829308d7cf"; 
	//公司名称
	private static String product = "数之星"; 
	//短信模式
	private static String smsType = "normal"; 
	//签名名称
	private static String signName = "数之星"; 
	//验证码模板ID
	private static String sendVerificationCode = "SMS_58295139"; 
	//短信模板ID
	private static String sendMessageCode = "SMS_58335087"; 
	//公司固定电话
	private static String telephone = "028-65788695"; 
	
	//发送验证码
	@Override
	public void sendVerification(String phone,String verification) {
		String msg = "{\"code\":\""+verification+"\",\"product\":\""+product+"\"}";
		TaobaoClient client = new DefaultTaobaoClient(normalUrl, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType(smsType);
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(msg);
		req.setRecNum(phone);
		req.setSmsTemplateCode(sendVerificationCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		System.out.println(rsp.getBody());
	}

	//发送短信
	@Override
	public void sendMessage(String phone,String account) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String msg = "{\"product\":\""+product+"\",\"name\":\""+account+"\",\"time\":\""+time+"\",\"telephone\":\""+telephone+"\"}";
		TaobaoClient client = new DefaultTaobaoClient(normalUrl, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType(smsType);
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(msg);
		req.setRecNum(phone);
		req.setSmsTemplateCode(sendMessageCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		System.out.println(rsp.getBody());
	}
	
}
