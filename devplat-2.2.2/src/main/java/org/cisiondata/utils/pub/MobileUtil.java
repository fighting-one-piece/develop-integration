package org.cisiondata.utils.pub;

/** 
 * 获取手机运营商 
 * ClassName：MobileUtil 
 * @author LiZheng 
 * @param args  
 * @date May 30, 2012 8:58:53 AM 
 * @version 
 */  
public class MobileUtil {  
    /** 
     * 判断传入的参数号码为哪家运营商 
     * @param mobile 
     * @return 运营商名称 
     */  
    public static String validateMobile(String mobile){  
        String returnString="";  
        if(mobile.trim().substring(0,3).equals("134") ||  mobile.trim().substring(0,3).equals("135") ||   
                mobile.trim().substring(0,3).equals("136") || mobile.trim().substring(0,3).equals("137")    
                || mobile.trim().substring(0,3).equals("138")  || mobile.trim().substring(0,3).equals("139") ||  mobile.trim().substring(0,3).equals("150") ||   
                mobile.trim().substring(0,3).equals("151") || mobile.trim().substring(0,3).equals("152")    
                || mobile.trim().substring(0,3).equals("157") || mobile.trim().substring(0,3).equals("158") || mobile.trim().substring(0,3).equals("159")   
                 || mobile.trim().substring(0,3).equals("187") || mobile.trim().substring(0,3).equals("188")){  
            returnString="1";   //中国移动  
        }  
        if(mobile.trim().substring(0,3).equals("130") ||  mobile.trim().substring(0,3).equals("131") ||   
                mobile.trim().substring(0,3).equals("132") || mobile.trim().substring(0,3).equals("156")    
                || mobile.trim().substring(0,3).equals("185")  || mobile.trim().substring(0,3).equals("186")){  
            returnString="2";   //中国联通  
        }  
        if(mobile.trim().substring(0,3).equals("133") ||  mobile.trim().substring(0,3).equals("153") ||   
                mobile.trim().substring(0,3).equals("180") || mobile.trim().substring(0,3).equals("189")){  
            returnString="3";   //中国电信  
        }  
        if(returnString.trim().equals("")){  
            returnString="0";   //未知运营商  
        }  
        return returnString;  
    }  
}  