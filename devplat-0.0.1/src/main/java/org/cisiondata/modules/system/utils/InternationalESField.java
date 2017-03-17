package org.cisiondata.modules.system.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cisiondata.modules.system.entity.ESMetadata;
import org.cisiondata.utils.exception.BusinessException;

public class InternationalESField {

	public static void main(String[] args) throws Exception {
		writeToFile();
		System.out.println(">>>>>>>>>>>>>>>>>SUCCESS");
	}
	
	private static void writeToFile() throws Exception{
		File fileCN = new File("src\\main\\resources\\locale\\esmetadata_zh_CN.properties");
		File fileEN = new File("src\\main\\resources\\locale\\esmetadata_en_US.properties");
		if(fileCN.exists()){
			fileCN.delete();
		}
		if(fileEN.exists()){
			fileEN.delete();
		}
		try {
			fileCN.createNewFile();
			fileEN.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("创建国际化文件失败");
		}
		List<ESMetadata> list = getAll();
		BufferedWriter bwCN=
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileCN),"ISO-8859-1"));
		BufferedWriter bwEN=
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileEN),"ISO-8859-1"));
		StringBuilder CNStringBuilder = new StringBuilder();
		StringBuilder ENStringBuilder = new StringBuilder();
		Map<String,String> basemap = initBaseMap();
		for(Entry<String, String> entry : basemap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			ENStringBuilder.delete( 0, ENStringBuilder.length());
			CNStringBuilder.delete( 0, CNStringBuilder.length());
			if(key.lastIndexOf(".") == -1){
				CNStringBuilder.append(key).append("=").append(value);
				ENStringBuilder.append(key).append("=").append(key);
			} else {
				int n = key.lastIndexOf(".");
				CNStringBuilder.append(key).append("=").append(value);
				ENStringBuilder.append(key).append("=").append(key.substring(n+1, key.length()));
			}
			bwCN.write(CNStringBuilder.toString());
			bwEN.write(ENStringBuilder.toString());
			bwCN.newLine();
			bwEN.newLine();
		}
		
		for(ESMetadata data : list){
			ENStringBuilder.delete( 0, ENStringBuilder.length());
			CNStringBuilder.delete( 0, CNStringBuilder.length());
			CNStringBuilder.append(data.getIndexs()).append(".").append(data.getType()).append(".").append(data.getAttribute_en())
				.append("=").append(data.getAttribute_ch());
			ENStringBuilder.append(data.getIndexs()).append(".").append(data.getType()).append(".").append(data.getAttribute_en())
				.append("=").append(data.getAttribute_en());
			bwCN.write(CNStringBuilder.toString());
			bwEN.write(ENStringBuilder.toString());
			bwCN.newLine();
			bwEN.newLine();
			//工商特殊处理
			if("business".equals(data.getType())){
				ENStringBuilder.delete( 0, ENStringBuilder.length());
				CNStringBuilder.delete( 0, CNStringBuilder.length());
				CNStringBuilder.append(data.getIndexs()).append("_new.").append(data.getType()).append(".").append(data.getAttribute_en())
					.append("=").append(data.getAttribute_ch());
				ENStringBuilder.append(data.getIndexs()).append("_new.").append(data.getType()).append(".").append(data.getAttribute_en())
					.append("=").append(data.getAttribute_en());
				bwCN.write(CNStringBuilder.toString());
				bwEN.write(ENStringBuilder.toString());
				bwCN.newLine();
				bwEN.newLine();
			}
		}

		bwCN.flush();
		bwEN.flush();
		bwCN.close();
		bwEN.close();
	}
	
	private static Map<String,String> initBaseMap(){
		Map<String,String> basemap = new HashMap<String,String>();
		basemap.put("financial", toEncodedUnicode(("财务"),false));
		basemap.put("financial_new",toEncodedUnicode(("财务"),false) );
		basemap.put("financial.logistics", toEncodedUnicode(("物流"),false));
		basemap.put("financial.finance", toEncodedUnicode(("金融"),false));
		basemap.put("financial.business", toEncodedUnicode(("工商"),false));
		basemap.put("financial_new.business", toEncodedUnicode(("工商"),false));
		basemap.put("financial.house", toEncodedUnicode(("物业楼盘"),false));
		basemap.put("financial.car", toEncodedUnicode(("车主信息"),false));
		basemap.put("operator", toEncodedUnicode(("基础信息"),false));
		basemap.put("operator.telecom", toEncodedUnicode(("电信运营商"),false));
		basemap.put("other", toEncodedUnicode(("其他信息"),false));
		basemap.put("other.contact", toEncodedUnicode(("通讯录信息"),false));
		basemap.put("other.internet", toEncodedUnicode(("互联网信息"),false));
		basemap.put("other.bocaioriginal", toEncodedUnicode(("博彩数据"),false));
		basemap.put("work", toEncodedUnicode(("工作信息"),false));
		basemap.put("work.resume", toEncodedUnicode(("工作简历信息"),false));
		basemap.put("work.socialsecurity", toEncodedUnicode(("社保信息"),false));
		basemap.put("work.qualification", toEncodedUnicode(("资格考试"),false));
		basemap.put("work.student", toEncodedUnicode(("学生信息"),false));
		basemap.put("work.cybercafe", toEncodedUnicode(("网吧信息"),false));
		basemap.put("work.hospital", toEncodedUnicode(("医院信息"),false));
		basemap.put("work.elder", toEncodedUnicode(("老人信息"),false));
		basemap.put("work.accumulationfund", toEncodedUnicode(("公积金信息"),false));
		basemap.put("work.motherandbady", toEncodedUnicode(("母婴信息"),false));
		basemap.put("work.healthproducts", toEncodedUnicode(("保健品信息"),false));
		basemap.put("trip", toEncodedUnicode(("出行信息"),false));
		basemap.put("trip.airplane", toEncodedUnicode(("航空信息"),false));
		basemap.put("trip.hotel", toEncodedUnicode(("旅馆信息"),false));
		basemap.put("qq", toEncodedUnicode(("QQ信息"),false));
		basemap.put("qq.qqdata", toEncodedUnicode(("QQ信息"),false));
		basemap.put("qq.qqqunrelation", toEncodedUnicode(("QQ群关系"),false));
		basemap.put("qq.qqqundata", toEncodedUnicode(("QQ群信息"),false));
		basemap.put("email", toEncodedUnicode(("邮箱信息"),false));
		basemap.put("email.email", toEncodedUnicode(("邮箱信息"),false));
		basemap.put("account", toEncodedUnicode(("账号信息"),false));
		basemap.put("account.account", toEncodedUnicode(("账号信息"),false));
		basemap.put("account.accountjd", toEncodedUnicode(("京东账号信息"),false));
		basemap.put("account.accountht", toEncodedUnicode(("台湾账号信息"),false));
		return basemap;
	}
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://192.168.0.114:3306/devplat?useUnicode=true&characterEncoding=UTF-8";
	    String username = "root";
	    String password = "123";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private static List<ESMetadata> getAll() {
	    Connection conn = getConn();
	    String sql = "select * from T_ES_METADATA order by type";
	    PreparedStatement pstmt;
	    List<ESMetadata> list = new ArrayList<ESMetadata>();
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	ESMetadata esmetadata = new ESMetadata();
	        	esmetadata.setIndexs(rs.getString("indexs"));
	        	esmetadata.setType(rs.getString("type"));
//	        	esmetadata.setAttribute_ch(cnToUnicode(rs.getString("attribute_ch")));
	        	esmetadata.setAttribute_ch(toEncodedUnicode(rs.getString("attribute_ch"),false));
//	        	esmetadata.setAttribute_ch(rs.getString("attribute_ch"));
	        	esmetadata.setAttribute_en(rs.getString("attribute_en"));
	        	list.add(esmetadata);
	        }
	        rs.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	
    /** 
     * 将字符串编码成 Unicode 形式的字符串. 如 "黄" to "\u9EC4" 
     * Converts unicodes to encoded \\uxxxx and escapes 
     * special characters with a preceding slash 
     *  
     * @param theString 
     *        待转换成Unicode编码的字符串。 
     * @param escapeSpace 
     *        是否忽略空格，为true时在空格后面是否加个反斜杠。 
     * @return 返回转换后Unicode编码的字符串。 
     */  
    public static String toEncodedUnicode(String theString, boolean escapeSpace) {  
        int len = theString.length();  
        int bufLen = len * 2;  
        if (bufLen < 0) {  
            bufLen = Integer.MAX_VALUE;  
        }  
        StringBuffer outBuffer = new StringBuffer(bufLen);  
  
        for (int x = 0; x < len; x++) {  
            char aChar = theString.charAt(x);  
            // Handle common case first, selecting largest block that  
            // avoids the specials below  
            if ((aChar > 61) && (aChar < 127)) {  
                if (aChar == '\\') {  
                    outBuffer.append('\\');  
                    outBuffer.append('\\');  
                    continue;  
                }  
                outBuffer.append(aChar);  
                continue;  
            }  
              
            switch (aChar) {  
            case ' ':  
                if (x == 0 || escapeSpace) outBuffer.append('\\');  
                outBuffer.append(' ');  
                break;  
            case '\t':  
                outBuffer.append('\\');  
                outBuffer.append('t');  
                break;  
            case '\n':  
                outBuffer.append('\\');  
                outBuffer.append('n');  
                break;  
            case '\r':  
                outBuffer.append('\\');  
                outBuffer.append('r');  
                break;  
            case '\f':  
                outBuffer.append('\\');  
                outBuffer.append('f');  
                break;  
            case '=': // Fall through  
            case ':': // Fall through  
            case '#': // Fall through  
            case '!':  
                outBuffer.append('\\');  
                outBuffer.append(aChar);  
                break;  
            default:  
                if ((aChar < 0x0020) || (aChar > 0x007e)) {  
                    // 每个unicode有16位，每四位对应的16进制从高位保存到低位  
                    outBuffer.append('\\');  
                    outBuffer.append('u');  
                    outBuffer.append(toHex((aChar >> 12) & 0xF));  
                    outBuffer.append(toHex((aChar >> 8) & 0xF));  
                    outBuffer.append(toHex((aChar >> 4) & 0xF));  
                    outBuffer.append(toHex(aChar & 0xF));  
                } else {  
                    outBuffer.append(aChar);  
                }  
            }  
        }  
        return outBuffer.toString();  
    } 
    private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',  
            'B', 'C', 'D', 'E', 'F' };  
  
    private static char toHex(int nibble) {  
        return hexDigit[(nibble & 0xF)];  
    }  
}
