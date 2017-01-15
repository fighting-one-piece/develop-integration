package org.cisiondata.modules.identity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.elasticsearch.service.IESService;
import org.cisiondata.modules.identity.service.IQQService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("qqService")
public class QQServiceImpl implements IQQService {
	
	@Resource(name = "esService")
	private IESService esService = null;
	
	//根据QQ号码得到群详情和群昵称
	@Override
	public List<Map<String, Object>> readQQDatas(String qq) {
		List<String> listqun = new ArrayList<String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		List<String> listQ = AllList(qq);
		if (listQ.size() > 0) {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			for (int i = 0; i < listQ.size(); i++) {
				boolQueryBuilder.should(QueryBuilders.termQuery("qunNum", listQ.get(i)));
			}
			listMap = esService.readDataListByCondition("qq", "qqqundata", boolQueryBuilder);
			for (int i = 0; i < listMap.size(); i++) {
				listqun.add(listMap.get(i).get("QQ群号").toString());
				list.add(listMap.get(i));
			}
			// 开始
			if (listqun.size() > 0) {
				for (int i = 0; i < listqun.size(); i++) {
					BoolQueryBuilder qb = QueryBuilders.boolQuery();
					qb.must(QueryBuilders.termQuery("qunNum", listqun.get(i)))
							.must(QueryBuilders.termQuery("qqNum", qq));
					List<Map<String, Object>> listnick = esService.readDataListByCondition("qq", "qqqunrelation", qb);
					for (int j = 0; j < listnick.size(); j++) {
						list.get(i).put("nick", listnick.get(j).get("QQ昵称"));
					}
				}
			}
		}
		return list;
	}
	
	// 根据QQ号获取QQ群号
	private List<String> AllList(String qq) {
		List<Map<String, Object>> listMap = esService.readDataListByCondition("qq", "qqqunrelation", QueryBuilders.termQuery("qqNum", qq));
		List<String> list = new ArrayList<String>();
		for (int i = 0, len = listMap.size(); i < len; i++) {
			list.add(listMap.get(i).get("QQ群号").toString());
		}
		return list;
	}

	@Override
	// 根据QQ群号得到对应的群信息
	public List<Map<String, Object>> readQQqunDatas(String qunNum) {
		return esService.readDataListByCondition("qq", "qqqunrelation", QueryBuilders.termQuery("qunNum", qunNum));
	}

	@Override
	// 根据QQ号得到基本信息
	public List<Map<String, Object>> readQQData(String qq) {
		return esService.readDataListByCondition("qq", "qqdata", QueryBuilders.termQuery("qqNum", qq));
	}

	//通过QQ昵称查询
	@Override
	public QueryResult<Map<String, Object>> readQQNickData(String nick, String scrollId, int rowNumPerPage) {
		return esService.readPaginationDataListByCondition("qq", "qqqunrelation", 
				QueryBuilders.matchPhraseQuery("nick", nick), scrollId, rowNumPerPage);
	}
}
