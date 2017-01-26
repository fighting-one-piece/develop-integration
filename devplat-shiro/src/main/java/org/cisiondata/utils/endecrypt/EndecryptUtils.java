package org.cisiondata.utils.endecrypt;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class EndecryptUtils {
	
	public static final String SALT = "cisiondata";

	/** 
     * base64进制加密 
     * @param text 
     * @return 
     */ 
    public static String encrytBase64(String text) { 
        return Base64.encodeToString(text.getBytes()); 
    } 
    
    /** 
     * base64进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptBase64(String cipherText) { 
        return Base64.decodeToString(cipherText); 
    } 
    
    /** 
     * 16进制加密 
     * @param text 
     * @return 
     */ 
    public static String encrytHex(String text) { 
        return Hex.encodeToString(text.getBytes()); 
    } 
    
    /** 
     * 16进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptHex(String cipherText) { 
        return new String(Hex.decode(cipherText)); 
    } 
    
    public static String generateKey() { 
        AesCipherService aesCipherService = new AesCipherService(); 
        Key key = aesCipherService.generateNewKey(); 
        return Base64.encodeToString(key.getEncoded()); 
    } 
    
    /** 
     * 对密码进行md5加密,并返回密文和salt 
     * @param username 用户名 
     * @param password 密码 
     * @return 密文和salt 
     */ 
    public static String md5Password(String username, String password, String salt){ 
//        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator(); 
//        String salt= secureRandomNumberGenerator.nextBytes().toHex(); 
        //组合username,两次迭代，对密码进行加密 
        return new Md5Hash(password, username + salt, 2).toBase64(); 
    } 
    
    public static String encryptPassword(String username, String password) {
    	return encryptPassword(username, password, SALT);
    }
    
    public static String encryptPassword(String username, String password, String salt) {
    	DefaultHashService hashService = new DefaultHashService(); 
    	//默认算法SHA-512
    	hashService.setHashAlgorithmName("SHA-512");  
    	//私盐，默认无  
    	hashService.setPrivateSalt(new SimpleByteSource(username + salt));
    	//是否生成公盐，默认false 
    	hashService.setGeneratePublicSalt(true); 
    	//用于生成公盐。默认就这个  
    	hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
    	//生成Hash值的迭代次数  
    	hashService.setHashIterations(2); 
    	  
    	HashRequest request = new HashRequest.Builder().setAlgorithmName("SHA-512")
    			.setSource(ByteSource.Util.bytes(password))
    				.setSalt(ByteSource.Util.bytes(username + salt))
    					.setIterations(2).build();  
    	return hashService.computeHash(request).toHex();
    }
    
    public static void main(String[] args) {
		System.out.println(encryptPassword("cisiondata", "cisiondata", "cisiondata"));
	}
    
    
}
