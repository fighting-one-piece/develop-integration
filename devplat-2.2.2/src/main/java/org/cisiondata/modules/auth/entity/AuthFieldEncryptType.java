package org.cisiondata.modules.auth.entity;

/** 接口域加密类型*/
public enum AuthFieldEncryptType {
	
	/** 不加密*/
	NO_ENCRYPT(0), 
	/** 手机号加密*/
	MOBILE_ENCRYPT(1), 
	/** 邮箱加密*/
	EMAIL_ENCRYPT(2), 
	/** QQ加密*/
	QQ_ENCRYPT(3), 
	/** 密码加密*/
	PASSWORD_ENCRYPT(4),
	/** 身份证号加密*/
	IDCARD_ENCRYPT(5), 
	/** 地址加密*/
	ADDRESS_ENCRYPT(6), 
	/** 时间加密*/
	TIME_ENCRYPT(7), 
	/** 照片加密*/
	PHOTO_ENCRYPT(8), 
	/** 姓名加密*/
	NAME_ENCRYPT(9), 
	/** 银行卡加密*/
	BANK_CARD_ENCRYPT(10), 
	/** 公积金卡加密*/
	ACCUMULATION_FUND_CARD_ENCRYPT(10);
	
	private int value;
	
	private AuthFieldEncryptType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
	
	public static AuthFieldEncryptType getFieldEncryptType(int value){
		switch (value) {
		case 0:
			return AuthFieldEncryptType.NO_ENCRYPT;
		case 1:
			return AuthFieldEncryptType.MOBILE_ENCRYPT;
		case 2:
			return AuthFieldEncryptType.EMAIL_ENCRYPT;
		case 3: 
			return AuthFieldEncryptType.QQ_ENCRYPT;
		case 4: 
			return AuthFieldEncryptType.PASSWORD_ENCRYPT;
		case 5:
			return AuthFieldEncryptType.IDCARD_ENCRYPT;
		case 6:
			return AuthFieldEncryptType.ADDRESS_ENCRYPT;
		case 7:
			return AuthFieldEncryptType.TIME_ENCRYPT;
		case 8:
			return AuthFieldEncryptType.PHOTO_ENCRYPT;
		case 9:
			return AuthFieldEncryptType.NAME_ENCRYPT;
		case 10:
			return AuthFieldEncryptType.BANK_CARD_ENCRYPT;
		case 11:
			return AuthFieldEncryptType.ACCUMULATION_FUND_CARD_ENCRYPT;
		default:
			return AuthFieldEncryptType.NO_ENCRYPT;
		}
	}

}
