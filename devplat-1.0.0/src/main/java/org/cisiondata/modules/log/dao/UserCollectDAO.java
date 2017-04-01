package org.cisiondata.modules.log.dao;

import java.util.List;

import org.cisiondata.modules.log.entity.UserCollect;
import org.springframework.stereotype.Repository;
@Repository("userCollectDAO")
public interface UserCollectDAO {
	//用户收藏
	public int addUserCollect(UserCollect coll);
	//展示
	public List<UserCollect> selCollect(String account);
}
