package org.cisiondata.modules.system.service;

import java.io.IOException;
import java.util.Map;

import org.cisiondata.modules.system.entity.SensitiveWord;
import org.cisiondata.utils.exception.BusinessException;

public interface ISensitiveWordService {
	//接受全部的敏感词
	public  Map<String,Object> SensitiveAll(int page, int pageSize)throws BusinessException, IOException;
	
	//根据ID查询
	public SensitiveWord FindID(long ID);
	
	//验证
	public int vserification(String word);
	
	//修改
	public int Setpdate(long updateid,String word,String updateInitialCount) throws IOException;
	
	//增加
	public int AddSensitive(String sitive) throws IOException;
	
	//删除操作
	public int Sendelete(long deleteId, String deleteCount) throws IOException;
	
	//修改唯一验证
	public int SoleJudgment(long updateid,String word,String updateInitialCount)throws IOException;
	
	//新增的唯一验证
	public int AddJudgment(String sitive)throws IOException;
	
}
