package org.cisiondata.modules.identity.service;

import org.cisiondata.utils.exception.BusinessException;

public interface IHongGuService {
	//朋友网url查找qq号，编号为1的操作
	public String pywToQQ(String url) throws BusinessException;
	//微博url查找qq号，编号为2的操作
	public String wbToQQ(String weibo) throws BusinessException;
	// qq查找朋友网，编号为3的操作
	public String qqToPyw(String QQ) throws BusinessException;
	//qq反找微博，编号为4的操作
	public String qqTowb(String QQ) throws BusinessException;
	//对查找qq发起临时会话，编号为5的操作
	public String temporaryWindow(String QQ) throws BusinessException;
	//qq查找最后说说，编号为6的操作
	public String qqLastShuoShuo(String QQ) throws BusinessException;
	//手机号找qq，编号为7的操作
	public String phoneNumToQQ(String num) throws BusinessException;
}
