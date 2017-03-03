package org.cisiondata.modules.analysis.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.ftp.SftpClient;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;

@Service("dataRecordStatisticsService")
public class DataRecordStatisticsServiceImpl extends DataRecordServiceImpl {

	private static Map<String, String> nameMap_114 = null;
	private static Map<String, String> nameMap_115 = null;
	private static String nowDate = null;
	
	static{
		nameMap_115 = new HashMap<String,String>();
		nameMap_115.put("xx", "谢鑫");
		nameMap_115.put("hzj", "何泽杰");
		nameMap_115.put("jsq", "蒋声强");
		nameMap_115.put("skm", "宋彪");
		nameMap_115.put("wse", "王仕恩");
		nameMap_115.put("wx", "王旭");
		nameMap_115.put("xp", "肖鹏");
		
		nameMap_114 = new HashMap<String,String>();
		nameMap_114.put("fb", "方斌");
		nameMap_114.put("hx", "何霞");
		nameMap_114.put("ljp", "李建鹏");
		nameMap_114.put("lx", "李欣");
		nameMap_114.put("ly", "刘宇");
		nameMap_114.put("tjl", "谭佳龙");
		nameMap_114.put("ysw", "杨仕伟");
	}
	
	
	/**
	 * 读取当天导表日志，获取文件数与记录数，存入数据库
	 */
	public void insertDocNumAndDataNum(int day) throws BusinessException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
			//后一天记录前一天导入数据 减1
			//周一 记录周六导入数据 减2
			//周一 记录周五导入数据 减3
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		date = calendar.getTime();
		nowDate = sdf.format(date);
//		nowDate = "2016-11-02";
		
		SftpClient sftpClient = new SftpClient();
		ChannelSftp sftp = null;
		String url = DataRecordStatisticsServiceImpl.class.getResource("").toString().replaceAll("file:/", "")+"123.log";
		try {
			//114
			sftp = sftpClient.connect("192.168.0.114", 22, "ym", "youmeng@123");
			for (Entry<String, String> entry : nameMap_114.entrySet()){
				Set<String> set = new HashSet<String>();
				String ename = entry.getKey();
				Integer dataNum = 0;
				Integer docNum = 0;
				Vector<LsEntry> vector = sftpClient.listFiles("/home/"+ename+"/DataX/datax/log", sftp);
				for (LsEntry lsEntry : vector) {
					if (!nowDate.equals(lsEntry.getFilename()))continue;
					Vector<LsEntry> fileVector = sftpClient.listFiles("/home/"+ename+"/DataX/datax/log/"+nowDate, sftp);
					for (LsEntry fileLsEntry : fileVector){
						String fileName = fileLsEntry.getFilename();
						if (".".equals(fileName) || "..".equals(fileName))continue;
//						System.out.println("/home/"+ename+"/DataX/datax/log/"+nowDate+"/"+fileName);
						sftpClient.download("/home/"+ename+"/DataX/datax/log/"+nowDate, fileName,url, sftp);
						//读取log文件 获得表数与记录数
						Map<String, Object> dataMap = readLocalFile(url,set);
						dataNum += Integer.valueOf(String.valueOf(dataMap.get("dataNum")));
						docNum += Integer.valueOf(String.valueOf(dataMap.get("docNum")));
					}
				}
				super.insertAndReadList(entry.getValue(), dataNum, docNum);
				System.out.println(entry.getValue()+":"+"dataNum "+dataNum+",docNum "+docNum);
			}
			sftpClient.closeConnection(sftp);
			
			//115
			sftp = sftpClient.connect("192.168.0.115", 22, "ym", "youmeng@123");
			for (Entry<String, String> entry : nameMap_115.entrySet()){
				Set<String> set = new HashSet<String>();
				String ename = entry.getKey();
				Integer dataNum = 0;
				Integer docNum = 0;
				Vector<LsEntry> vector = sftpClient.listFiles("/home/"+ename+"/DataX/datax/log", sftp);
				for (LsEntry lsEntry : vector) {
					if (!nowDate.equals(lsEntry.getFilename()))continue;
					Vector<LsEntry> fileVector = sftpClient.listFiles("/home/"+ename+"/DataX/datax/log/"+nowDate, sftp);
					for (LsEntry fileLsEntry : fileVector){
						String fileName = fileLsEntry.getFilename();
						if (".".equals(fileName) || "..".equals(fileName))continue;
//						System.out.println("/home/"+ename+"/DataX/datax/log/"+nowDate+"/"+fileName);
						sftpClient.download("/home/"+ename+"/DataX/datax/log/"+nowDate, fileName,url, sftp);
						//读取log文件 获得表数与记录数
						Map<String, Object> dataMap = readLocalFile(url,set);
						dataNum += Integer.valueOf(String.valueOf(dataMap.get("dataNum")));
						docNum += Integer.valueOf(String.valueOf(dataMap.get("docNum")));
					}
				}
				super.insertAndReadList(entry.getValue(), dataNum, docNum);
				System.out.println(entry.getValue()+":"+"dataNum "+dataNum+",docNum "+docNum);
			
			}
			sftpClient.closeConnection(sftp);
			deleteFile(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sftpClient.closeConnection(sftp);
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  Map<String ,Object>  readLocalFile(String path,Set<String> set) throws IOException{
		//读取文件
		  FileInputStream fis = new FileInputStream(path);   
		  InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
		  BufferedReader br = new BufferedReader(isr);
		  //表个数
		  Integer docNum =0;
		  //记录数
		  Integer dataNum  = 0;
		  boolean flag = false;
		  boolean flag1 = true;
		  boolean flag2 = false;
		  String line = null;
		  String[] str=null;
		  String strLine=null;
		  while ((line = br.readLine()) != null) {
			  if(line.contains("\"table\"")){
				  if ((line=br.readLine()) != null){
					  str= line.split(",");
					  strLine =line;
					  flag2 =true;
					  if(set.contains(line)){
						  flag2=false;
					  }
				  }
			  }
			  if(line.contains("\"192.168.0.114\"")){
				  flag1=false;
			  }
			  if(line.contains("读出记录总数")){
				  Pattern p = Pattern.compile("\\d+");
				  Matcher m = p.matcher(line);
				  flag = true;
				  if(m.find()){
					  dataNum = Integer.parseInt(m.group());
				  }
			  }
		  }
		  Map<String ,Object> map = new HashMap<String ,Object>();
		  if(flag && flag1 && flag2){
			  if(str!=null){
				  docNum=str.length;
			  }
			  set.add(strLine);
			  map.put("docNum", docNum);
			  map.put("dataNum", dataNum);
		  }else{
			  map.put("docNum", 0);
			  map.put("dataNum", 0);
		  }
		  map.put("set", set);
		  fis.close();
		  isr.close();
		  return map;
	}
	//删除文件
	public void deleteFile(String path){
		File file = new File(path);
		if (file.exists())file.delete();
	}
	
	
}
