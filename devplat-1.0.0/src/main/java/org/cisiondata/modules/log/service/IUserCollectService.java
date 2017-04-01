package org.cisiondata.modules.log.service;

import java.util.List;

import org.cisiondata.modules.log.entity.UserCollect;

public interface IUserCollectService {
	//用户收藏
	public void addUserCollect(String account,String type,String collect);
	//展示
	public List<UserCollect> selCollect(String account);
}
