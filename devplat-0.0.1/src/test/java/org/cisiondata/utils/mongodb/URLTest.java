package org.cisiondata.utils.mongodb;

import org.cisiondata.utils.http.HttpUtils;

public class URLTest {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("aaa").append("\r\n");
		sb.append("bbb");
		System.out.println(sb.toString());
		String response = HttpUtils.sendGet("http://118.244.140.159:56666/cms.php?a=ef6d3c79c83953a58fb7807fe6ba980dfc402d29b5e02b81");
		System.out.println(response);
	}
	
}
