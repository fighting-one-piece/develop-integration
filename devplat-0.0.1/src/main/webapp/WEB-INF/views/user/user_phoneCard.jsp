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
<input id="query" placeholder="请输入手机号码或者身份证" style="width: 220px"  type="text"  onkeydown="EnterPressPhoneIdCard();"/>
<input id="submitt" type="submit" value="搜索" />
<div id="error">	
  </div>
</div>
<div id="topResults" align="center">
</div>
<div id="label" align="center">
	
</div>
<div id="bottomResults" align="center"></div>
<div id="index_inde"></div>
<table class="menu">
	<tr>
		
	</tr>
    </table>
</body>
</html>