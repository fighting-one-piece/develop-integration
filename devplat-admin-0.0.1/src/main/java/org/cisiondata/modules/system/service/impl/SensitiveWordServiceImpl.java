package org.cisiondata.modules.system.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.system.dao.SensitiveWordDAO;
import org.cisiondata.modules.system.entity.SensitiveWord;
import org.cisiondata.modules.system.service.ISensitiveWordService;
import org.cisiondata.utils.exception.BusinessException;
import org.cisiondata.utils.redis.RedisClusterUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements ISensitiveWordService, InitializingBean {
	
	@Resource(name="sensitiveWordDAO")
	private SensitiveWordDAO sensitiveWordDAO=null;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<SensitiveWord> dataList = sensitiveWordDAO.findAll();
		if (null == dataList || dataList.size() == 0) return;
		String[] words = new String[dataList.size()];
		for (int i = 0, len = dataList.size(); i < len; i++) {
			words[i] = dataList.get(i).getWord();
		}
		//RedisUtils.getInstance().setsAddMember("sensitive_word", words);
		RedisClusterUtils.getInstance().sadd("sensitive_word", words);
	}

	@Override
	public SensitiveWord FindID(long ID) {
		SensitiveWord sen=sensitiveWordDAO.FinID(ID);
		return sen;
	}

	@Override
	public int vserification(String word) {
		// TODO Auto-generated method stub
		int code=sensitiveWordDAO.Vserification(word);
		return code;
	}

	//修改
	@Override
	public int Setpdate(long updateid,String word,String updateInitialCount) throws IOException {
		RedisClusterUtils.getInstance().srem("sensitive_word", updateInitialCount);
		//RedisUtils.getInstance().setsDelMember("sensitive_word",updateInitialCount);
		//RedisUtils.getInstance().setsAddMember("sensitive_word", word);
		RedisClusterUtils.getInstance().sadd("sensitive_word", word);
		return sensitiveWordDAO.redupdate(word,updateid);
		
	}

	//增加
	@Override
	public int AddSensitive(String sitive) throws IOException {
		RedisClusterUtils.getInstance().sadd("sensitive_word", sitive);
		//RedisClusterUtils.getInstance().set("sensitive_word",sitive);
		return sensitiveWordDAO.AddSensitive(sitive);
	}

	//删除操作
	@Override
	public int Sendelete(long  deleteId,String deleteCount) throws IOException {
		RedisClusterUtils.getInstance().srem("sensitive_word", deleteCount);
		//RedisUtils.getInstance().setsDelMember("sensitive_word",deleteCount);
		long deleteid=Long.valueOf(deleteId);
		return sensitiveWordDAO.Sendelete(deleteid);
	}
	
	//分页显示全部
	@Override
	public Map<String, Object> SensitiveAll(int page,int pageSize) throws BusinessException, IOException {
		int count =sensitiveWordDAO.contun();
		int pageCount = count % pageSize == 0 ? count/pageSize : count/pageSize + 1;
		int begin = (page-1) * pageSize;
		List<SensitiveWord> read=sensitiveWordDAO.SensitiveAll(begin,pageSize);
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("data",read);
		map.put("pageNum", page);
		map.put("pageCount", pageCount);
		return map;
	}

}
