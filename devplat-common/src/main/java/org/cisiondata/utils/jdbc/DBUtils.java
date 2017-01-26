package org.cisiondata.utils.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(DBUtils.class);

	public static void databaseStatistics(String host, String database, String username, String password) {
		String dbUrl = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8";
		String countSQL = "select count(1) from `%s`";
		TreeMap<Long, String> map = new TreeMap<Long, String>();
		long totalCount = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ResultSet countRS = null;
		try {
			connection = DriverManager.getConnection(String.format(dbUrl, host, database), username, password);  
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
			System.out.println(map);
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
	}
	
	public static void databaseTableColumnStatistics(String host, String database, String username, String password) {
		String dbUrl = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8";
		String selectSQL = "select * from `%s` limit 1";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ResultSet selectRS = null;
		try {
			connection = DriverManager.getConnection(String.format(dbUrl, host, database), username, password);  
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			rs = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
			String sql = null;
			Map<String, List<String>> results = new HashMap<String, List<String>>();
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				sql = String.format(selectSQL, tableName);
				statement = connection.prepareStatement(sql);
				selectRS = statement.executeQuery(sql);
				ResultSetMetaData rsmd = selectRS.getMetaData();
				StringBuilder columnNameSB = new StringBuilder();
				for(int i = 1, len = rsmd.getColumnCount(); i <= len; i++){
					String columnName = rsmd.getColumnName(i);
					columnNameSB.append(columnName).append(",");
				}
				String columntNameAppend = columnNameSB.toString();
				List<String> tableNames = results.get(columntNameAppend);
				if (null == tableNames) {
					tableNames = new ArrayList<String>();
					results.put(columntNameAppend, tableNames);
				}
				tableNames.add(tableName);
			}
			for (Map.Entry<String, List<String>> entry : results.entrySet()) {
				System.out.print(entry.getKey() + " : ");
				for (String tableName : entry.getValue()) {
					System.out.print(tableName + ",");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != connection) connection.close();
				if (null != statement) statement.close();
				if (null != rs) rs.close();
				if (null != selectRS) selectRS.close();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	public static void databaseTableColumnStatistics(String host, String database, String username, String password,
			int start, int end) {
		String dbUrl = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8";
		String selectSQL = "select * from `%s` limit 1";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ResultSet selectRS = null;
		try {
			connection = DriverManager.getConnection(String.format(dbUrl, host, database), username, password);  
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			rs = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
			List<String> tableNameList = new ArrayList<String>();
			int index = 0;
			while (rs.next() && (index < end)) {
				if (index >= start) {
					tableNameList.add(rs.getString("TABLE_NAME"));
				}
				index++;
			}
			Map<String, List<String>> results = new HashMap<String, List<String>>();
			String sql = null;
			for (int i = 0, len = tableNameList.size(); i < len; i++) {
				String tableName = tableNameList.get(i);
				sql = String.format(selectSQL, tableName);
				statement = connection.prepareStatement(sql);
				selectRS = statement.executeQuery(sql);
				ResultSetMetaData rsmd = selectRS.getMetaData();
				StringBuilder columnNameSB = new StringBuilder();
				for(int j = 1, jLen = rsmd.getColumnCount(); j <= jLen; j++){
					String columnName = rsmd.getColumnName(j);
					columnNameSB.append(columnName).append(",");
				}
				String columntNameAppend = columnNameSB.toString();
				List<String> tableNames = results.get(columntNameAppend);
				if (null == tableNames) {
					tableNames = new ArrayList<String>();
					results.put(columntNameAppend, tableNames);
				}
				tableNames.add(tableName);
			}
			for (Map.Entry<String, List<String>> entry : results.entrySet()) {
				System.out.print(entry.getKey() + " : ");
				for (String tableName : entry.getValue()) {
					System.out.print(tableName + ",");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != connection) connection.close();
				if (null != statement) statement.close();
				if (null != rs) rs.close();
				if (null != selectRS) selectRS.close();
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String[] args) {
		databaseStatistics("192.168.0.115", "bocai", "root", "123");
//		databaseTableColumnStatistics("192.168.0.115", "bocai", "root", "123", 0, 100);
//		System.out.println("######");
//		databaseTableColumnStatistics("192.168.0.115", "bocai", "root", "123", 100, 200);
	}
	
}
