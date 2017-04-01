package org.cisiondata.modules.identity.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.identity.HongGuUtils;
import org.cisiondata.modules.identity.service.IHongGuService;
import org.cisiondata.utils.exception.BusinessException;
import org.springframework.stereotype.Service;


@Service("hongGuService")
public class HongGuServiceImpl implements IHongGuService {


	/**
	 * 朋友网url查找qq号，编号为1的操作
	 * 
	 * @param url
	 *            String
	 * @return QQ String
	 */
	@Override
	public Map<String,String> pywToQQ(String url) throws BusinessException {
		Map<String,String> map = new HashMap<String, String>();
		String pywtoqq = HongGuUtils.requestPCHonggu(url, 1).split("朋友网")[0].trim().split("机器码")[0].trim();
		if (pywtoqq == null || "".equals(pywtoqq)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		int index = pywtoqq.lastIndexOf("-");
		if (index == -1) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), pywtoqq.replaceAll("若有疑问请联系Q：593131939免费解决", ""));
		}
		map.put("朋友网名称", pywtoqq.substring(0, index));
		map.put("QQ账号", pywtoqq.substring(index+1));
		return map;
	}

	/**
	 * 微博url查找qq号，编号为2的操作
	 * 
	 * @param weibo
	 *            String
	 * @return QQ String
	 */
	@Override
	public Map<String,String> wbToQQ(String weibo) throws BusinessException {
		Map<String,String> map = new HashMap<String, String>();
		String separator1 = "<wb>";
		String separator2 = "</wb>";
		String request = separator1 + weibo + separator2;
		String msg = HongGuUtils.requestPCHonggu(request, 2).split("机器码")[0].trim();
		if (msg == null || "".equals(msg)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		if (msg.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}
		int index = msg.lastIndexOf("--");
		if (index == -1) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		map.put("微博名称", msg.substring(0, index));
		map.put("QQ账号", msg.substring(index+2));
		return map;
	}

	/**
	 * qq查找朋友网，编号为3的操作
	 * 
	 * @param QQ
	 *            String
	 * @return urlForPengyouwang String
	 */
	@Override
	public Map<String,String> qqToPyw(String QQ) throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		String separator1 = "<qqpyw>";
		String separator2 = "</qqpyw>";
		String request = separator1 + QQ + separator2;
		String a = HongGuUtils.requestPCHonggu(request, 3).replace("\b", "");
		if (a == null || "".equals(a)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		if (a.contains("不能两次查同号。先查10001错开")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "不能连续查询同一个QQ，请稍后查询");
		}
		if (a.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}
		String base2 = "http://118.244.140.159:56666/cms.php?a=";
		String base1 = "http://profile.pengyou.com/index.php?mod=profile&u=";
		map.put("朋友网地址",base1 + a );
		map.put("个人信息地址",base2 + a );
		return map;
	}

	/**
	 * qq反找微博，编号为4的操作
	 * 
	 * @param QQ
	 *            String
	 * @return QQtoWeibo String
	 */
	@Override
	public Map<String,Object> qqTowb(String QQ) throws BusinessException {
		String separator1 = "<qqpyw>";
		String separator2 = "</qqpyw>";
		String request = separator1 + QQ + separator2;
		String wbBase = "http://t.qq.com/";
		String result = HongGuUtils.requestPCHonggu(request, 4);
		System.out.println(result);
		Map<String,Object> map = new HashMap<String,Object>();
		if(result == null || "".equals(result.trim()) || !result.contains("(@") || !result.contains(")") || result.length() <= 3){
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		} 
		if (result.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}	
			map.put("微博名", result);
			String[] strs = result.split("\\(@");
			String[] strs1 = strs[1].split("\\)");
			map.put("微博地址",wbBase+ strs1[0]);
		return map;
	}

	/**
	 * 对查找qq发起临时会话，编号为5的操作
	 * 
	 * @param QQ
	 *            String
	 * @return QQComand String
	 */
	@Override
	public String temporaryWindow(String QQ) throws BusinessException {
		String a = HongGuUtils.requestPCHonggu("", 5);
		String r = a.split("uin=")[1].split("&Site")[0];
		String qqsession = a.replace(r, QQ).trim();
		if (qqsession == null || "".equals(qqsession)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		if (qqsession.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}
		return qqsession;
	}

	/**
	 * qq查找最后说说，编号为6的操作
	 * 
	 * @param QQ
	 *            String
	 * @return qqLastShuoShuo String
	 */
	@Override
	public Map<String, String> qqLastShuoShuo(String QQ) throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		String separator1 = "<wb>";
		String separator2 = "</wb>";
		String request = separator1 + QQ + separator2;
		String last = HongGuUtils.requestPCHonggu(request, 6).trim();
		if (last == null || "".equals(last)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		if (last.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}
			String[] arr1 = last.split("说说内容:");
			String[] arr2 = arr1[0].split("最后一条说说时间：");
			String[] arr3 = arr2[0].split("说说数：");
			if(arr1.length == 1){
				map.put("说说内容", "");
			} else {
				map.put("说说内容", arr1[1].trim());
			}
			map.put("最后说说时间", arr2[1].trim());
			map.put("说说总条数", arr3[1].trim());
		return map;
	}

	/**
	 * 手机号找qq，编号为7的操作
	 * 
	 * @param PhoneNum
	 *            String
	 * @return QQ String
	 */
	@Override
	public String phoneNumToQQ(String num) throws BusinessException {
		String separator1 = "<qqpyw>";
		String separator2 = "</qqpyw>";
		String request = separator1 + num + separator2;
		String phone =  HongGuUtils.requestPhoneHongda(request).trim();
		if (phone == null || "".equals(phone)) {
			throw new BusinessException(ResultCode.NOT_FOUNT_DATA.getCode(), ResultCode.NOT_FOUNT_DATA.getDesc());
		}
		if ( "没绑有QQ".equals(phone)) {
			throw new BusinessException(ResultCode.NOT_BINDING_QQ.getCode(), "未绑定QQ");
		}
		if (phone.contains("服务器占用中。请5秒后重点查询")) {
			throw new BusinessException(ResultCode.FAILURE.getCode(), "服务器占用中，请5秒后重点查询");
		}
		return phone;
	}

}
