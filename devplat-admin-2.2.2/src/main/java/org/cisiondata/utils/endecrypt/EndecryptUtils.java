package org.cisiondata.utils.endecrypt;

public class EndecryptUtils {
	
	public static final String SALT = "cisiondata";
	
	public static String encryptPassword(String password) {
		return SHAUtils.SHA512(new StringBuilder().append(password).append(SALT).toString());
	}

    public static String encryptPassword(String password, String salt) {
    	return SHAUtils.SHA512(new StringBuilder().append(password).append(salt).toString());
    }
    
    public static String encryptPassword(String account, String password, String salt) {
    	return SHAUtils.SHA512(new StringBuilder().append(account).append(password).append(salt).toString());
    }
    
    public static void main(String[] args) {
    	String salt = IDUtils.genUUID();
    	System.out.println(salt);
		System.out.println(encryptPassword("@#test456", salt));
		System.out.println(encryptPassword("cisiondata", "cisiondata").length());
	}
    
    
}
