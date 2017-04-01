package org.cisiondata.modules.auth.entity;

/** 接口域加密类型*/
public enum FieldEncryptType {
	
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
	
	private FieldEncryptType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
	
	public static FieldEncryptType getFieldEncryptType(int value){
		switch (value) {
		case 0:
			return FieldEncryptType.NO_ENCRYPT;
		case 1:
			return FieldEncryptType.MOBILE_ENCRYPT;
		case 2:
			return FieldEncryptType.EMAIL_ENCRYPT;
		case 3: 
			return FieldEncryptType.QQ_ENCRYPT;
		case 4: 
			return FieldEncryptType.PASSWORD_ENCRYPT;
		case 5:
			return FieldEncryptType.IDCARD_ENCRYPT;
		case 6:
			return FieldEncryptType.ADDRESS_ENCRYPT;
		case 7:
			return FieldEncryptType.TIME_ENCRYPT;
		case 8:
			return FieldEncryptType.PHOTO_ENCRYPT;
		case 9:
			return FieldEncryptType.NAME_ENCRYPT;
		case 10:
			return FieldEncryptType.BANK_CARD_ENCRYPT;
		case 11:
			return FieldEncryptType.ACCUMULATION_FUND_CARD_ENCRYPT;
		default:
			return FieldEncryptType.NO_ENCRYPT;
		}
	}

}
