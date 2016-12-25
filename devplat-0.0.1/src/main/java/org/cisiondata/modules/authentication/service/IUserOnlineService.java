package org.cisiondata.modules.authentication.service;

import java.util.Date;
import java.util.List;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.service.IGenericService;
import org.cisiondata.modules.authentication.entity.UserOnline;

public interface IUserOnlineService extends IGenericService<UserOnline, Long> {

	/**
     * 上线
     * @param userOnline
     */
    public void insertOnline(UserOnline userOnline);

    /**
     * 下线
     * @param id
     */
    public void deleteOnline(String id);

    /**
     * 批量下线
     * @param needOfflineIdList
     */
    public void deleteOnlineBatch(List<String> needOfflineIdList);

    /**
     * 无效的UserOnline
     * @return
     */
    public QueryResult<UserOnline> findExpiredUserOnlineList(Date expiredDate);
	
}
