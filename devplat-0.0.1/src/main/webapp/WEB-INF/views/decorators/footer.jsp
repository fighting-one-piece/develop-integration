<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<h4 align="center">
友情链接:
<a href="http://www.baidu.com" target="_blank">百度</a>
<a href="http://cn.bing.com" target="_blank">必应</a>
<a href="http://s.weibo.com/top/summary" target="_blank">微博搜人</a>
<a href="http://gsxt.saic.gov.cn/" target="_blank">企业信用信息公示系统</a>
<a href="http://ditu.amap.com" target="_blank">高德地图</a>
<a href="http://www.raincent.com/" target="_blank">网络大数据</a>
</h4>
<script type="text/javascript" src="<%=basePath %>/js/modules/log/time.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/user/data_analysis.js"></script>