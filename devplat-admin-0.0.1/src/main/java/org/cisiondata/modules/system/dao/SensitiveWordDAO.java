package org.cisiondata.modules.system.dao;

import java.util.List;

import org.cisiondata.modules.system.entity.SensitiveWord;
import org.springframework.stereotype.Repository;

/**
 * dao层
 * @author Administrator
 *
 */
@Repository("sensitiveWordDAO")
public interface SensitiveWordDAO {
	
	//查询所有的敏感词  分页
	public List<SensitiveWord> SensitiveAll(int begin, int end);
	
	//根据ID查询记录
	public  SensitiveWord FinID(Long ID);
	
	//验证修改和新增的值的唯一性
	public int Vserification(String word);
	
	//修改数据
	public int redupdate( String word,long updateid);
	
	//增加数据
	public int AddSensitive(String sitive);
	
	//删除操作
	public int Sendelete(long deleteId);
	
	//查询所有数据的条数
	public int contun();
	
	//查询所有的敏感词
	public List<SensitiveWord> findAll();
}
