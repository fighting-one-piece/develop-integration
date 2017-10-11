package org.cisiondata.utils.endecrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5AppPubUtil {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String aa=EncoderByMd5("123456");
		System.out.println(aa);
	}
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	 MessageDigest md5=MessageDigest.getInstance("MD5");
    	 String newstr=byte2HexStr(md5.digest(str.getBytes("utf-8")));
    	 return newstr;
    }
    
    public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
		}
		return sb.toString().trim();
	}
//golang_demo     
//    package main
//
//    import ( 
//        "crypto/md5" 
//        "encoding/hex" 
//        "fmt" 
//    )
//
//    func main() { 
//        h := md5.New() 
//        h.Write([]byte("123456")) // 需要加密的字符串为 123456 
//        cipherStr := h.Sum(nil) 
//        fmt.Println(cipherStr) 
//        fmt.Printf("%s\n", hex.EncodeToString(cipherStr)) // 输出加密结果 
//    }
//
//    代码输入效果：e10adc3949ba59abbe56e057f20f883e



}
