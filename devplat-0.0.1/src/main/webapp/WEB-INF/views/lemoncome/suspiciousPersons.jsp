<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<h5 align="center">&nbsp;</h5>
	<div align="center">
	<p>提示：电话和身份证不能同时为空！</p>
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入电话号码"/></p>
	<p><input id="idCard" type="text"  style="width: 220px;" placeholder="请输入身份证号码"/></p>
	<p><input id="staffType" type="text"  style="width: 220px;" placeholder="请输入可疑人员类型1-4"/></p>
	<p><input id="lemonSubmittwo" type="button" value="确定"/></p>
</div>
<div id="resultEducation" align="center" style="top: 30%;position: absolute;"></div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
</body>
</html>