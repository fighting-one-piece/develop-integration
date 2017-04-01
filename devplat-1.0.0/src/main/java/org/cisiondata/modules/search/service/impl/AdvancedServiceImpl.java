package org.cisiondata.modules.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.cisiondata.modules.abstr.entity.QueryResult;
import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.auth.entity.ResourceInterfaceField;
import org.cisiondata.modules.search.service.IAdvancedService;
import org.cisiondata.modules.search.service.IESBizService;
import org.cisiondata.modules.search.util.FieldsUtils;
import org.cisiondata.modules.user.service.IResourceService;
import org.cisiondata.utils.exception.BusinessException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service("advancedService")
public class AdvancedServiceImpl implements IAdvancedService {

	private static final String CN_REG = "[\\u4e00-\\u9fa5]+";
	
	@Resource(name = "esBizService")
	private IESBizService esBizService;
	
	@Resource(name = "aresourceService")
	private IResourceService resourceService;
	
	@Override
	public Map<String, Object> readPaginationDataListByMultiCondition(HttpServletRequest req, String index, String type,
			String query, String scrollId, Integer rowNumPerPage) throws BusinessException {
		if(StringUtils.isBlank(query)){
			throw new BusinessException(ResultCode.KEYWORD_NOT_NULL);
		}
		String[] qus = query.split(",");
		if(qus.length < 1 || rowNumPerPage == null || StringUtils.isBlank(index) || StringUtils.isBlank(type)){
			throw new BusinessException(ResultCode.PARAM_ERROR);
		}
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		Pattern pattern = Pattern.compile(CN_REG);
		for(int i = 0; i < qus.length; i++){
			String[] qu = qus[i].split(":");
			if(qu.length != 2) throw new BusinessException(ResultCode.PARAM_ERROR);
			qu[1] = qu[1].trim();
			
			if(StringUtils.isBlank(qu[1])) throw new BusinessException(ResultCode.PARAM_ERROR);
			
			//判断参数是否合法
			if (qu[0].endsWith("mobilePhone") || qu[0].endsWith("telePhone") ||
					qu[0].endsWith("MobilePhone") || qu[0].endsWith("TelePhone")){
				if (qu[1].length() < 6) throw new BusinessException(ResultCode.PARAM_ERROR);
			} else if (qu[0].endsWith("idCard") || qu[0].endsWith("IdCard")) {
				if (qu[1].length() < 15) throw new BusinessException(ResultCode.PARAM_ERROR);
			} else if (pattern.matcher(qu[1]).find()) {
				if (qu[1].length() < 2) throw new BusinessException(ResultCode.PARAM_ERROR);
			}
			
			boolean isChinese = pattern.matcher(qu[1]).find();
			if (isChinese) {
				qb.must(QueryBuilders.matchPhraseQuery(qu[0], qu[1]));
			} else{
				qb.must(QueryBuilders.termQuery(qu[0], qu[1]));
			}
		}
		QueryResult<Map<String, Object>> qr = esBizService.readPaginationDataListByCondition(
				index, new String[] { type }, qb, scrollId, rowNumPerPage,true);
		
		if(qr.getResultList().size() == 0) throw new BusinessException(ResultCode.NOT_FOUNT_DATA);
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<ResourceInterfaceField> list = resourceService.findAttributeByUrl(req);
		if (null == list || list.size()==0) throw new BusinessException(ResultCode.DATABASE_READ_FAIL);
		Map<String,String> fieldsMap = FieldsUtils.getFieldsMessageSource(list);
		qr = FieldsUtils.filterQueryResultByFields(qr, list);
		map.put("data", qr);
		map.put("head", fieldsMap);
		return map;
	}

}
