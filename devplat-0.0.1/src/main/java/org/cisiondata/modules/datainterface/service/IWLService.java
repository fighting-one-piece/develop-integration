package org.cisiondata.modules.datainterface.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface IWLService {
	//获取数据
	public Map<String, Object> readData(String phone) throws NoSuchAlgorithmException ;
}
