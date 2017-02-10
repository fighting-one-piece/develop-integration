package org.cisiondata.modules.analysis.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.cisiondata.modules.analysis.service.IDBService;
import org.cisiondata.utils.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("dbService")
public class DBServiceImpl implements IDBService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);
	
	/** 数据库URL*/
    private String dbUrl = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8";
    /** 默认数据库HOST*/
    private String defaultHost = "192.168.0.114";
	/** 数据库用户名*/
    private String dbUsername = "root";
    /** 数据库用户密码 */
    private String dbPassword = "123";
    /** 数据库表数据数查询*/
    private String countSQL = "select count(1) from `%s`";
    /** 数据库表数据数查询*/
    private String sql = "select TABLE_NAME,TABLE_ROWS from information_schema.TABLES where TABLE_SCHEMA='%s'";
    
    static {
    	try {
			Driver driver = (Driver) (Class.forName("com.mysql.jdbc.Driver").newInstance());
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			LOG.info(e.getMessage(), e);
		}
    }
	
	@Override
	public List<Map.Entry<String, Long>> readDBTables(String dbName) throws BusinessException {
		return readDBTables(defaultHost, dbName);
	}
	
	@Override
	public List<Map.Entry<String, Long>> readDBTables(String dbHost, String dbName) throws BusinessException {
		Map<String, Long> map = new HashMap<String, Long>();
		long totalCount = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(String.format(dbUrl, dbHost, dbName), dbUsername, dbPassword);  
			String csql = String.format(sql, dbName);
			statement = connection.prepareStatement(csql);
			rs = statement.executeQuery(csql);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				long tableRows = rs.getLong("TABLE_ROWS");
				totalCount += tableRows;
				map.put(tableName, tableRows);
			}
			map.put("totalCount", totalCount);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		} finally {
			try {
				if (null != connection) connection.close();
				if (null != statement) statement.close();
				if (null != rs) rs.close();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		List<Map.Entry<String, Long>> entries = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Long>>(){
			@Override
			public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return entries;
	}
	
	public Map<Long, String> readDBTablesByCount(String dbHost, String dbName) throws BusinessException {
		TreeMap<Long, String> map = new TreeMap<Long, String>();
		long totalCount = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ResultSet countRS = null;
		try {
			connection = DriverManager.getConnection(String.format(dbUrl, dbHost, dbName), dbUsername, dbPassword);  
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			rs = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
			String sql = null;
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				sql = String.format(countSQL, tableName);
				statement = connection.prepareStatement(sql);
				countRS = statement.executeQuery(sql);
				long tableCount = 0;
				if (countRS.next()) {
					tableCount = countRS.getInt(1);
					map.put(tableCount, tableName);
					totalCount += tableCount;
				}
			}
			map.put(totalCount, "totalCount");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != connection) connection.close();
				if (null != statement) statement.close();
				if (null != rs) rs.close();
				if (null != countRS) countRS.close();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return map;
	}
	
	

}
