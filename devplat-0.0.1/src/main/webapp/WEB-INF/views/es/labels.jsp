<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<title>手机身份证分类查询</title>
</head>
<body>
 <h5 align="center">&nbsp;</h5>
<div align="center">
<input id="query" placeholder="请输入关键字" style="width: 220px"  type="text"  onkeydown="EnterPressapp();"/>
<input id="submitapp" type="submit" value="搜索" />
</div>
<div id="appresult" align="center">
</div>
<div id="appresults" align="center">

</div>
<br>
<div align="center">
	<input id="nextapp" style="display: none;" value="下一页" type="button"/>
</div>
<!-- 搜索背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
	<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
</div>
<script type="text/javascript" src="<%=basePath %>/js/modules/appsearch/appsearch.js"></script>
</body>
</html>