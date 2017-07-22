package org.mybatis.plugins.pagination;

public class MySQL5Dialect implements Dialect {

    @Override
	public String obtainPaginationSql(String sql, int offset, int limit) {
    	StringBuffer sb = new StringBuffer(sql);
    	sb.append(" limit ").append(offset).append(",").append(limit);
        return sb.toString();
    }

    public boolean supportsLimit() {
        return true;
    }

}
