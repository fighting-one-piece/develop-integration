package org.cisiondata.modules.abstr.web;

public enum ResultCode {

	SUCCESS(1, "操作成功"),
	FAILURE(2, "操作失败"),
	NOT_FOUNT_DATA(3, "未查询到数据"),
	SYSTEM_IS_BUSY(5, "系统繁忙，请稍后再试"),
	QUERY_FAILURE(7, "查询失败"),
	
	PARAM_NULL(-101, "请求参数为空"),
	PARAM_ERROR(-102, "请求参数错误或非法请求"),
	PARAM_FORMAT_ERROR(-103, "请求参数格式错误"),
	
	DATA_EXISTED(-110, "数据已存在"),
	URL_MAPPING_ERROR(-120, "URL Mapping错误"),
	PAGE_NOT_FOUND(-190, "页面不存在"),
	
	DATABASE_CONNECTION_FAIL(-201, "数据库连接失败"),
	DATABASE_READ_FAIL(-202, "数据库读取失败"),
	DATABASE_OPERATION_FAIL(-203, "数据库操作失败"),
	
	VERIFICATION_SUCCESS(200, "验证通过成功返回"),
	VERIFICATION_FAIL(400, "验证失败，cookie或token值无效"),
	VERIFICATION_URL_ERROR(401, "URL语法错误或参数值非法，或access_token无效"),
	VERIFICATION_NO_IP_PERIMISSION(410, "服务器没有开通接口的IP权限"),
	VERIFICATION_NO_EXIST(420, "用户不存在"),
	VERIFICATION_ID_INVALID(427, "id参数无效，需要重新初始化id"),
	VERIFICATION_USER_FAIL(428, "用户认证失败,请重新登录"),
	
	SERVER_ERROR(500, "服务器端错误"),
	SERVER_UNDER_MAINTENANCE(503, "服务器正在维护"),
	ACCOUNT_EXISTED(-552,"账号已存在"),
	ACCOUNT_NOT_EXIST(540, "账号不存在"),
	ACCOUNT_PASSWORD_NOT_MATCH(550, "账号密码不匹配"),
	ACCOUNT_EXPIRED_OR_DELETED(551, "账户已过期或已删除"),
	ACCOUNT_BALANCE_INSUFFICIENT(552, "账户余额不足"),
	PASSWORD_NOT_MATCH(553, "密码为6至16位数字及字符组成"),
	
	SECURITY_ANSWER_ERROR(560, "输入错误，您还有2次机会"),
	SECURITY_NOT_FOUNT(561, "未找到密保问题"),
	SECOND_SECURITY_ANSWER_ERROR(562, "输入错误，您还有1次机会"),
	THIRD_SECURITY_ANSWER_ERROR(563, "输入错误，您已达到输入上限，单击“忘记密保”重新找回"),
	MOBILEPHONE_SEND_MESSAGE_ERROR(564, "短信发送失败，请稍后再试"),
	
	NOT_BINDING_QQ(601, "手机号未绑定QQ"),
	VERIFICATION_CODE_FAILURE(602, "验证码校验失败"),
	KEYWORD_NOT_NULL(603, "关键字不能为空"),
	MACADDRESS_ERROR(604, "mac地址不正确"),
	
	MOBILEPHONE_FORMAT_ERROR(605, "电话号码格式不正确"),
	EMAIL_FORMAT_ERROR(614,"邮箱格式不正确"),
	IDCARD_FORMAT_ERROR(611,"身份证号码格式不正确"),
	MOBILEPHONE_EXIST(606, "电话号码已存在,请更换电话号码"),
	SECURITY_NULL(607, "密保问题为空"),
	SECURITY_ERROR(608, "密保问题答案不匹配"),
	CANT_SEND_VERIFICATION(609, "不能连续发送验证码"),
	MOBILEPHONE_ERROR(610, "电话号码不匹配"),
	RESOURCE_NOT_EXIST(650, "资源不存在或已删除"),
	IPADDRESS_ERROR(651, " 您的IP地址未备案，请联系客服（页面底部）"),
	NOT_VERIFY_MOBILEPHONE(652, "未验证原手机号"),
	KEY_WORD_SHORT(653, "关键词过短"),
	UNAUTHORIZED_ACCESS_USER_LOG(654,"无权访问此用户日志"),
	UNAUTHORIZED_ACCESS_COMPANY_USER(655,"无权查询单位中的用户"),
	UNAUTHORIZED_DELETION_COMPANY_USER(656,"无权停用单位中的用户"),
	TWO_PASSWORDS_ARE_INCONSISTENT(611,"两次密码不一致"),
	ACCESS_ID_EXISTED(612,"ACCESS_ID已存在"),
	IP_ERROR(613,"某个IP格式有误"),
	MOUDLE_NAME_EXIST(614,"模块已存在"),
	IDENTITY_NAME_EXIST(615,"标识已存在"),
	REQUSET_CONVERSION_FAILURE(700,"request转换失败"),
	RECHARGE_PACKAGE_NOT_EXIST(701, "套餐已删除或不存在"),
	MOBILEPHONE_EXISTED(702,"电话号码存在");
	
	
	
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
