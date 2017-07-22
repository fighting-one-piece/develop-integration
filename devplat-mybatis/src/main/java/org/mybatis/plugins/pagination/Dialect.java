package org.mybatis.plugins.pagination;

public interface Dialect {

	public static enum Type {
		
        MYSQL("mysql"),
        MSSQL("sqlserver"),
        ORACLE("oracle");
        
        private String value = null;
        
        private Type(String value) {
        	this.value = value;
        }
        
        public String getValue() {
        	return this.value;
        }
        
    }

    /**
     * @descrption 获取分页SQL
     * @param sql 原始查询SQL
     * @param offset 开始记录索引（从零开始）
     * @param limit 每页记录大小
     * @return 返回数据库相关的分页SQL语句
     */
    public abstract String obtainPaginationSql(String sql, int offset, int limit);
}
