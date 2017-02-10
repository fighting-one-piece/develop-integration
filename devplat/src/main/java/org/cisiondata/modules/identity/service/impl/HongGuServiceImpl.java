package org.cisiondata.modules.identity.service.impl;

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
	public String pywToQQ(String url) throws BusinessException {
		return HongGuUtils.requestPCHonggu(url, 1).split("朋友网")[0].trim();
	}

	/**
	 * 微博url查找qq号，编号为2的操作
	 * 
	 * @param weibo
	 *            String
	 * @return QQ String
	 */
	@Override
	public String wbToQQ(String weibo) throws BusinessException {
		String separator1 = "<wb>";
		String separator2 = "</wb>";
		String request = separator1 + weibo + separator2;
		return HongGuUtils.requestPCHonggu(request, 2).split("机器码")[0].trim();
	}

	/**
	 * qq查找朋友网，编号为3的操作
	 * 
	 * @param QQ
	 *            String
	 * @return urlForPengyouwang String
	 */
	@Override
	public String qqToPyw(String QQ) throws BusinessException {
		String separator1 = "<qqpyw>";
		String separator2 = "</qqpyw>";
		String request = separator1 + QQ + separator2;
		String a = HongGuUtils.requestPCHonggu(request, 3).replace("\b", "");
		String base2 = "http://118.244.140.159:56666/cms.php?a=";
		String base1 = "http://profile.pengyou.com/index.php?mod=profile&u=";
		return base1 + a + "\n" + base2 + a;
	}

	/**
	 * qq反找微博，编号为4的操作
	 * 
	 * @param QQ
	 *            String
	 * @return QQtoWeibo String
	 */
	@Override
	public String qqTowb(String QQ) throws BusinessException {
		String separator1 = "<qqpyw>";
		String separator2 = "</qqpyw>";
		String request = separator1 + QQ + separator2;
		String wbBase = "http://t.qq.com/";
		String result = HongGuUtils.requestPCHonggu(request, 4);
		return result + "\n" + wbBase
				+ result.substring(0, result.indexOf("("));
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
		return a.replace(r, QQ);
	}

	/**
	 * qq查找最后说说，编号为6的操作
	 * 
	 * @param QQ
	 *            String
	 * @return qqLastShuoShuo String
	 */
	@Override
	public String qqLastShuoShuo(String QQ) throws BusinessException {
		String separator1 = "<wb>";
		String separator2 = "</wb>";
		String request = separator1 + QQ + separator2;
		return HongGuUtils.requestPCHonggu(request, 6);
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
		return HongGuUtils.requestPCHonggu(request, 7);
	}

}
