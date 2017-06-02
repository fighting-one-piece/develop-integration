package org.cisiondata.utils.jdbc;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
	
	/**
	 * 处理结果集
	 * @param resultSet
	 * @return
	 */
	public T handle(ResultSet resultSet);
}
