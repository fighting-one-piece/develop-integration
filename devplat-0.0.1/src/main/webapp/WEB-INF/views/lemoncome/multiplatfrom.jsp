<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号码"/></p>
	<p><input id="multiSubmit" type="button" value="确定"/></p>
</div>
<div id="multiplatfrom" align="center" style="top: 15%;position: absolute;left: 33%;">
</div>
</body>
</html>
