package org.cisiondata.modules.analysis.controller;

import javax.annotation.Resource;

import org.cisiondata.modules.abstr.web.ResultCode;
import org.cisiondata.modules.abstr.web.WebResult;
import org.cisiondata.modules.analysis.service.IDBService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DBController {
	
	@Resource(name = "dbService")
	private IDBService dbService = null;
	
	@RequestMapping(value = "/db/tables/statistics", method = RequestMethod.GET)
	public String readDBTablesStatisticsView() {
		return "/user/db_data";
	}
	
	@ResponseBody
	@RequestMapping(value = "/db/{dbName}/tables/statistics", method = RequestMethod.GET)
	public WebResult readDBTablesStatistics(@PathVariable String dbName) {
		WebResult result = new WebResult();
		try {
			result.setData(dbService.readDBTables(dbName));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/host/{dbHost}/db/{dbName}/tables/statistics", method = RequestMethod.GET)
	public WebResult readDBTablesStatistics(@PathVariable String dbHost, @PathVariable String dbName) {
		WebResult result = new WebResult();
		try {
			result.setData(dbService.readDBTables(dbHost, dbName));
			result.setCode(ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			result.setCode(ResultCode.FAILURE.getCode());
			result.setFailure(e.getMessage());
		}
		return result;
	}

}
