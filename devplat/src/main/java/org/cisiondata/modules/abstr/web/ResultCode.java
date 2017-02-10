package org.cisiondata.modules.abstr.web;

public enum ResultCode {

	SUCCESS(1, "操作成功"),
	FAILURE(2, "操作失败"),
	PARA_NULL(-101,"参数为空"),
	POSTDATA_ERROR(-102,"请求数据错误或非法请求"),
	URL_ERROR(-103,"页面不存在"),
	
	DATABASE_CONNECTION_FAIL(-201,"数据库连接失败"),
	DATABASE_READ_FAIL(-202,"数据库读取失败"),
	DATABASE_OPERATION_FAIL(-203,"数据库操作失败"),
	
	VERIFICATION_SUCCESS(200,"验证通过成功返回"),
	VERIFICATION_FAIL(400,"验证失败，cookie或token值无效"),
	VERIFICATION_URL_ERROR(401,"URL语法错误或参数值非法，或access_token无效"),
	VERIFICATION_NO_IP_PERIMISSION(410,"服务器没有开通接口的IP权限"),
	VERIFICATION_NO_EXIST(420,"用户不存在"),
	VERIFICATION_ID_INVALID(427,"id参数无效，需要重新初始化id"),
	VERIFICATION_SERVER_ERROR(500,"服务器端错误"),
	VERIFICATION_SERVER_UNDER_MAINTENANCE(503,"服务器正在维护");
	
	/** 代码值*/
	private int code = 0;
	/** 代码值描述*/
	private String desc = null;
	
	private ResultCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
