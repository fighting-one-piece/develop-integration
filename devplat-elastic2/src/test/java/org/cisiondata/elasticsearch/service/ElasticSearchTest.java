package org.cisiondata.elasticsearch.service;

import org.cisiondata.modules.es.plugins.stconverter.analysis.STConvertType;
import org.cisiondata.modules.es.plugins.stconverter.analysis.STConverter;

public class ElasticSearchTest {

	public static void main(String[] args) {
		STConverter converter = new STConverter();
		String t = converter.convert("龙", STConvertType.SIMPLE_2_TRADITIONAL);
		System.out.println(t);
		String s = converter.convert("劉,邝,粱,闾,黃,馮,馬,韓,陳,赫,許,苑,頋,趙,謝", STConvertType.TRADITIONAL_2_SIMPLE);
		System.out.println(s);
	}
	
}
